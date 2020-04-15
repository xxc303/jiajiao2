package com.bdu.jiajiao.dto;

/**
 * @author 123
 * @create 2020/3/9
 * @since 1.0.0
 */
public class OrderDTO {

    private String teacherName;
    private String price;
    private String item;
    private String studentName;
    private String teacherPhone;
    private String studentPhone;
    private String address;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "teacherName='" + teacherName + '\'' +
                ", price='" + price + '\'' +
                ", item='" + item + '\'' +
                ", studentName='" + studentName + '\'' +
                ", teacherPhone='" + teacherPhone + '\'' +
                ", studentPhone='" + studentPhone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
