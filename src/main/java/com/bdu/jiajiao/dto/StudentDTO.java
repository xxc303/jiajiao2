package com.bdu.jiajiao.dto;

/**
 * @author 123
 * @create 2020/2/12
 * @since 1.0.0
 * 老师发布详细信息
 */
public class StudentDTO {

    private int id;
    private String username;
    private String phone;
    private String sex;
    private String grade;
    private String school;
    private String score;
    private String detailRequirement;
    private String item;
    private String area;
    private String address;
    private String idRequirement;
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDetailRequirement() {
        return detailRequirement;
    }

    public void setDetailRequirement(String detailRequirement) {
        this.detailRequirement = detailRequirement;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdRequirement() {
        return idRequirement;
    }

    public void setIdRequirement(String idRequirement) {
        this.idRequirement = idRequirement;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", grade='" + grade + '\'' +
                ", school='" + school + '\'' +
                ", score='" + score + '\'' +
                ", detailRequirement='" + detailRequirement + '\'' +
                ", item='" + item + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", idRequirement='" + idRequirement + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
