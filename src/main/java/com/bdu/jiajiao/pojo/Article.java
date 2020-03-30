package com.bdu.jiajiao.pojo;

import java.util.Date;

/**
 * @author 123
 * @create 2020/3/25
 * @since 1.0.0
 */
public class Article {

    private int id;
    private String title;
    private String content;
    private String creator;
    private int creatorId;
    private Date createTime;
    private int type;//0学生，1老师，2管理员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId=" + creatorId +
                ", createTime=" + createTime +
                ", type=" + type +
                '}';
    }
}
