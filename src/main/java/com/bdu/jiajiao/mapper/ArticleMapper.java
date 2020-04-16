package com.bdu.jiajiao.mapper;

import com.bdu.jiajiao.pojo.Article;

import java.util.List;

/**
 * @author 123
 * @version 1.0
 * @date 2020 2020/3/9 15:25
 */
public interface ArticleMapper {

    int addArticle(Article article);

    List<Article> queryAllTeaArticle();

    List<Article> queryAllStuArticle();

    Article queryByName(String creator);

    Article queryById(int id);

    List<Article> queryAllArticle();

    int deleteById(int id);

    int updateArticle(Article article);

}
