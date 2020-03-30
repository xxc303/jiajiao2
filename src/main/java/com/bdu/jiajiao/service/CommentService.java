package com.bdu.jiajiao.service;

import com.bdu.jiajiao.pojo.Comment;
import com.bdu.jiajiao.pojo.Reply;

import java.util.List;

/**
 * @author 123
 * @version 1.0
 * @date 2020 2020/3/26 19:30
 */
public interface CommentService {

    List<Comment> queryAllComment(int pageNum, int pageSize);

    List<Reply> queryAllReply(int pageNum, int pageSize);
}
