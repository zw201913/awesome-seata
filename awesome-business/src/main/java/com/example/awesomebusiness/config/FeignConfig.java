package com.example.awesomebusiness.config;

import feign.Feign;
import feign.Logger;
import feign.okhttp.OkHttpClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author zouwei
 * @className FeignConfig
 * @date: 2022/9/18 19:12
 * @description:
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignConfig {

	@Getter
	@Setter
	@Configuration
	@ConfigurationProperties(prefix = "feign.okhttp")
	@ConditionalOnProperty(name = "feign.okhttp.enabled", havingValue = "true")
	protected static class OkHttpProperties {
		boolean followRedirects = true;
		// 链接超时时间，单位毫秒
		int connectTimeout = 5000;
		boolean disableSslValidation = false;
		// 读超时，单位毫秒
		int readTimeout = 5000;
		// 写超时，单位毫秒
		int writeTimeout = 5000;
		// 是否自动重连
		boolean retryOnConnectionFailure = true;
		// 最大空闲链接
		int maxIdleConnections = 10;
		// 默认保持5分钟
		long keepAliveDuration = 1000 * 60 * 5L;
	}

	/**
	 * 配置okhttp以及对应的链接池
	 */
	@Configuration(
			proxyBeanMethods = false
	)
	@ConditionalOnClass({OkHttpClient.class})
	@ConditionalOnMissingBean({okhttp3.OkHttpClient.class})
	@ConditionalOnProperty({"feign.okhttp.enabled"})
	protected static class OkHttpFeignConfiguration {
		private okhttp3.OkHttpClient okHttpClient;

		protected OkHttpFeignConfiguration() {
		}

		@Bean
		public okhttp3.OkHttpClient client(OkHttpClientFactory httpClientFactory, OkHttpProperties properties, OkHttpClientConnectionPoolFactory connectionPoolFactory) {
			this.okHttpClient = httpClientFactory.createBuilder(properties.isDisableSslValidation())
					// 链接超时时间
					.connectTimeout(properties.getConnectTimeout(), TimeUnit.MILLISECONDS)
					// 是否禁用重定向
					.followRedirects(properties.isFollowRedirects())
					//设置读超时
					.readTimeout(properties.getReadTimeout(), TimeUnit.MILLISECONDS)
					//设置写超时
					.writeTimeout(properties.getWriteTimeout(), TimeUnit.MILLISECONDS)
					// 链接失败是否重试
					.retryOnConnectionFailure(properties.isRetryOnConnectionFailure())
					//链接池
					.connectionPool(connectionPoolFactory.create(properties.getMaxIdleConnections(), properties.getKeepAliveDuration(), TimeUnit.MILLISECONDS))
					.build();
			return this.okHttpClient;
		}

		@PreDestroy
		public void destroy() {
			if (this.okHttpClient != null) {
				this.okHttpClient.dispatcher().executorService().shutdown();
				this.okHttpClient.connectionPool().evictAll();
			}
		}
	}

	/**
	 * 配置openFeign日志级别
	 *
	 * @return
	 */
	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
