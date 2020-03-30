//package com.bdu.jiajiao.controller;
//
//import com.bdu.jiajiao.mapper.ArticleMapper;
//import com.bdu.jiajiao.pojo.Article;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author 123
// * @create 2020/3/9
// * @since 1.0.0
// */
//@Controller
//public class OrderController {
//
//    @Autowired
//    private ArticleMapper articleMapper;
//
//    @RequestMapping("/article/{id}")
//    public String article(@PathVariable("id") int id){
//        Article article = articleMapper.queryById(id);
//
//    }
//
//}
