package com.example.awesomestorage.dao.entity;

public class StockTcc {
    private Integer id;

    private String commodityCode;

    private Integer count;

    private Integer preDeductCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode == null ? null : commodityCode.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPreDeductCount() {
        return preDeductCount;
    }

    public void setPreDeductCount(Integer preDeductCount) {
        this.preDeductCount = preDeductCount;
    }
}