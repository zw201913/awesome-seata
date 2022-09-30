package com.example.awesomestorage.service;

/**
 * @author zouwei
 * @className IStockService
 * @date: 2022/9/28 00:06
 * @description:
 */
public interface IStockService {

	Boolean deductStock(String commodityCode, int count);
}
