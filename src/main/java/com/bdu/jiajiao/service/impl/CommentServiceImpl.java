package com.bdu.jiajiao.service.impl;

import com.bdu.jiajiao.mapper.CommentMapper;
import com.bdu.jiajiao.pojo.Comment;
import com.bdu.jiajiao.pojo.Reply;
import com.bdu.jiajiao.service.CommentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 123
 * @create 2020/3/26
 * @since 1.0.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> queryAllComment(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> commentList = commentMapper.queryAllComment();
        return commentList;
    }

    @Override
    public List<Reply> queryAllReply(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Reply> replyList = commentMapper.queryAllReply();
        return replyList;
    }
}
