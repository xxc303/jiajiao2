package com.bdu.jiajiao.controller;

import com.bdu.jiajiao.dto.CommentCreateDTO;
import com.bdu.jiajiao.dto.ResultDTO;
import com.bdu.jiajiao.mapper.CommentMapper;
import com.bdu.jiajiao.pojo.Comment;
import com.bdu.jiajiao.pojo.Reply;
import com.bdu.jiajiao.pojo.Student;
import com.bdu.jiajiao.pojo.Teacher;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author 123
 * @create 2020/1/3
 * @since 1.0.0
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 提交评论
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("student");
        Teacher teacher= (Teacher) request.getSession().getAttribute("teacher");

        if (teacher != null){
            return ResultDTO.errorOf(501,"家教不能评论！");
        }else if (student == null){
            return ResultDTO.errorOf(500,"请先登录");
        }
        System.out.println(student.toString());
        System.out.println(commentCreateDTO.toString());
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setItem(commentCreateDTO.getItem());
        comment.setContent(commentCreateDTO.getContent());
        comment.setOverview(commentCreateDTO.getOverview());
        comment.setCommentatorId(student.getId());
        comment.setCommentator(student.getUsername());
        comment.setCreateTime(new Date());
        System.out.println(comment);
        commentMapper.addComment(comment);
        return ResultDTO.okOf();
    }

    /**
     * 获取评论
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping("/comment/{parentId}")
    public ResultDTO<List<Comment>> queryCommentList(@PathVariable("parentId") int parentId, Model model){

        List<Comment> comments = commentMapper.queryCommentByParentId(parentId);
        model.addAttribute("comments",comments);
        return ResultDTO.okOf(comments);
    }

    /**
     * 提交回复
     */
    @ResponseBody
    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public Object reply(@RequestBody Reply reply, HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("student");
        Teacher teacher= (Teacher) request.getSession().getAttribute("teacher");
        if (student == null && teacher == null){
            return ResultDTO.errorOf(500,"请先登录");
        }else if (student != null){
            reply.setUsername(student.getUsername());
        }else if (teacher != null){
            reply.setUsername(teacher.getUsername());
        }
        reply.setCreateTime(new Date());
        commentMapper.addReply(reply);
        return ResultDTO.okOf();
    }

    /**
     * 查询评论回复
     */
    @ResponseBody
    @RequestMapping("/reply/{commentId}")
    public ResultDTO<List<Reply>> queryReplyByCommentId(@PathVariable("commentId") int commentId, Model model){
        List<Reply> replies = commentMapper.queryReplyByCommentId(commentId);
        return ResultDTO.okOf(replies);
    }


}
