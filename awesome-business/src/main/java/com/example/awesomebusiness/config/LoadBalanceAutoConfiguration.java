package com.example.awesomebusiness.config;

import com.example.awesomebusiness.util.IPV4Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerBeanPostProcessorAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zouwei
 * @className LoadBalanceAutoConfiguration
 * @date: 2022/9/19 20:13
 * @description:
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = LoadBalanceAutoConfiguration.LoadBalanceConfig.class)
@AutoConfigureBefore({ReactorLoadBalancerClientAutoConfiguration.class,
		LoadBalancerBeanPostProcessorAutoConfiguration.class})
public class LoadBalanceAutoConfiguration {


	@Configuration(proxyBeanMethods = false)
	public static class LoadBalanceConfig {

		/**
		 * 服务列表提供者
		 *
		 * @param context
		 * @return
		 */
		@Bean
		@ConditionalOnProperty(value = "spring.cloud.loadbalancer.server-list-strategy", havingValue = "net-segment")
		public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
			ServiceInstanceListSupplier supplier = ServiceInstanceListSupplier.builder().withBlockingDiscoveryClient().withCaching().build(context);
			return new NetSegmentServiceInstanceListSupplier(supplier);
		}

		/**
		 * 自定义负载均衡器
		 *
		 * @param environment
		 * @param loadBalancerClientFactory
		 * @return
		 */
		@Bean
		@ConditionalOnProperty(name = "spring.cloud.loadbalancer.router-strategy", havingValue = "canary")
		public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
			String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
			// 在Spring上下文中声明了一个CanaryLoadBalancer
			return new CanaryLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
		}
	}

	/**
	 * 查询可用服务列表
	 */
	public static class NetSegmentServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {

		private InetUtils inetUtils;

		public NetSegmentServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
			super(delegate);
			inetUtils = new InetUtils(new InetUtilsProperties());
		}

		@Override
		public Flux<List<ServiceInstance>> get() {
			return getDelegate().get().map(this::filterByNetSegment);
		}

		private List<ServiceInstance> filterByNetSegment(List<ServiceInstance> instances) {
			InetAddress host = inetUtils.findFirstNonLoopbackAddress();
			if (host == null) {
				return instances;
			}
			String resourceIp = host.getHostAddress();
			List<ServiceInstance> targetList = new ArrayList<>();
			for (ServiceInstance instance : instances) {
				// 注意子网掩码的设置
				if (IPV4Util.checkSameSegmentByDefault(resourceIp, instance.getHost())) {
					targetList.add(instance);
				}
			}
			if (CollectionUtils.isEmpty(targetList)) {
				return instances;
			}
			return targetList;
		}
	}

	/**
	 * 自定义负载均衡器
	 */
	private static class CanaryLoadBalancer implements ReactorServiceInstanceLoadBalancer {

		private final String serviceId;
		private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

		CanaryLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
			this.serviceId = serviceId;
			this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		}

		@Override
		public Mono<Response<ServiceInstance>> choose(Request request) {
			ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
			return supplier.get(request).next().map((serviceInstances) -> this.processInstanceResponse(supplier, serviceInstances, request));
		}

		private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances, Request request) {
			Response<ServiceInstance> serviceInstanceResponse;
			if (CollectionUtils.isEmpty(serviceInstances)) {
				serviceInstanceResponse = new EmptyResponse();
			} else {
				ServiceInstance instance = doChoose(serviceInstances, request);

				serviceInstanceResponse = new DefaultResponse(instance);
			}
			if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
				((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
			}
			return serviceInstanceResponse;
		}

		private static final String MARKING_FLOW = "marking-flow";

		/**
		 * 筛选目标实例
		 *
		 * @param serviceInstances
		 * @param request
		 * @return
		 */
		private ServiceInstance doChoose(List<ServiceInstance> serviceInstances, Request request) {
			DefaultRequestContext context = (DefaultRequestContext) request.getContext();
			RequestData requestData = (RequestData) context.getClientRequest();
			HttpHeaders headers = requestData.getHeaders();
			// 1. 如果请求头中没有标记，那么随机选择一个实例
			// 获取请求头中的标记
			List<String> values = headers.get(String.format("%s-%s", this.serviceId, MARKING_FLOW));
			// 如果请求头中没有打标
			if (CollectionUtils.isEmpty(values)) {
				// 1.1 从现有的实例列表中随机选择一个实例
				return getRoundRobinInstance(serviceInstances);
			}
			// 2. 请求头中有标记，寻找目标实例，先收集满足要求的服务实例到列表中
			List<ServiceInstance> filterServiceInstances = new ArrayList<>();
			for (ServiceInstance instance : serviceInstances) {
				Map<String, String> metadata = instance.getMetadata();
				// 2.1 服务实例中没有metadata的跳过
				if (CollectionUtils.isEmpty(metadata)) {
					continue;
				}
				String value = metadata.get(MARKING_FLOW);
				// 2.2 服务实例中没有metadata对应的数值为空的跳过
				if (StringUtils.isBlank(value)) {
					continue;
				}
				// 2.3 满足要求
				if (values.contains(value)) {
					filterServiceInstances.add(instance);
				}
			}
			// 3. 从过滤好的实例列表中随机选择一个服务实例
			return getRoundRobinInstance(filterServiceInstances);
		}

		private ServiceInstance getRoundRobinInstance(List<ServiceInstance> instances) {
			// 如果没有可用节点，则返回空
			if (CollectionUtils.isEmpty(instances)) {
				return null;
			}
			// 随机找一个
			int pos = ThreadLocalRandom.current().nextInt(instances.size());
			ServiceInstance instance = instances.get(pos % instances.size());
			return instance;
		}
	}
}
