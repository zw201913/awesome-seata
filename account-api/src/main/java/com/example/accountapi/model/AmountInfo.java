package com.example.accountapi.model;

/**
 * @author zouwei
 * @className AmountInfo
 * @date: 2022/9/27 23:30
 * @description:
 */
public class AmountInfo {

	private String userId;

	private long amount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
