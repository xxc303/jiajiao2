package com.bdu.jiajiao.pojo;

import java.util.Date;

/**
 * @author 123
 * @create 2020/3/23
 * @since 1.0.0
 */
public class Reply {

    private int id;
    private int commentId;//评论Id
    private String username;//评论人姓名
    private String content;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
