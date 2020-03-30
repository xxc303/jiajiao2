package com.bdu.jiajiao.pojo;

import java.util.Date;

/**
 * @author 123
 * @create 2020/3/9
 * @since 1.0.0
 */
public class Order {

    private int id;
    private String teaName;//家教姓名
    private String teaPhone;//家教电话
    private String stuName;
    private String stuPhone;
    private String item;//学员item
    private String address;//辅导地址-学员地址
    private String price;
    private Date createTime;//创建时间
    private int type;//类型：默认为0，取消为1

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTeaPhone() {
        return teaPhone;
    }

    public void setTeaPhone(String teaPhone) {
        this.teaPhone = teaPhone;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", teaName='" + teaName + '\'' +
                ", teaPhone='" + teaPhone + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", item='" + item + '\'' +
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                '}';
    }
}
