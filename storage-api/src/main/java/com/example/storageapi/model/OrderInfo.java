package com.example.storageapi.model;

/**
 * @author zouwei
 * @className OrderInfo
 * @date: 2022/9/28 00:28
 * @description:
 */
public class OrderInfo {

	private String commodityCode;

	private int count;

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
