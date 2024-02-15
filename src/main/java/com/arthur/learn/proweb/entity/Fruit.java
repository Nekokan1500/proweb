package com.arthur.learn.proweb.entity;

public class Fruit {
    
    private String fname;
    private Double price;
    private Integer fcount;
    private String remark;

    public Fruit() {
    }

    public Fruit(String fname, Double price, Integer fcount, String remark) {
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }

    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getFcount() {
        return fcount;
    }
    public void setFcount(Integer fcount) {
        this.fcount = fcount;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit [fname=" + fname + ", price=" + price + ", fcount=" + fcount + ", remark=" + remark + "]";
    }
    
}
