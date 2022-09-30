package com.example.awesomebusiness.api;

import com.example.orderapi.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zouwei
 * @className OrderApiClient
 * @date: 2022/9/27 23:24
 * @description:
 */
@FeignClient(name = "order", contextId = "orderApi", path = "/order")
public interface OrderApiClient extends OrderApi {
}
