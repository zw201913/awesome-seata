package com.example.orderapi.model;

/**
 * @author zouwei
 * @className OrderInfo
 * @date: 2022/9/27 23:04
 * @description:
 */
public class OrderInfo {

	private String userId;
	/**
	 * 商品编码
	 */
	private String commodityCode;
	/**
	 * 数量
	 */
	private int count;
	/**
	 * 单价
	 */
	private long unitPrice;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}
}
