package com.example.awesomebusiness.api;

import com.example.accountapi.api.WalletApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * account：注册的服务名称；
 * contextId：注册到spring中的Bean名称，保证唯一性
 * path：对应服务端的requestMapping里面的value值，没有的话，可不写
 *
 * @author zouwei
 * @className AccountApiClient
 * @date: 2022/9/18 14:42
 * @description:
 */
@FeignClient(name = "account", contextId = "walletApi", path = "/wallet")
public interface WalletApiClient extends WalletApi {
}
