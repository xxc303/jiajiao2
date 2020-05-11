package com.bdu.jiajiao.dto;

/**
 * @author 123
 * @create 2020/2/12
 * @since 1.0.0
 * 老师发布详细信息
 */
public class TeacherDTO {

    private int id;
    private String username;
    private String age;
    private String phone;
    private String item;
    private String area;
    private String address;
    private String description;
    private String teachYear;
    private String price;
    private String title;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeachYear() {
        return teachYear;
    }

    public void setTeachYear(String teachYear) {
        this.teachYear = teachYear;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", item='" + item + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", teachYear='" + teachYear + '\'' +
                ", price='" + price + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
