package com.example.awesomeorder.api;

import com.example.storageapi.api.StockApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zouwei
 * @className StockApiClient
 * @date: 2022/9/28 00:34
 * @description:
 */
@FeignClient(name = "storage", contextId = "stockApi", path = "/stock")
public interface StockApiClient extends StockApi {
}
