package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Article;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.ArticleStatus;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:44
 */
public interface IArticleService extends IBaseService<Article, Integer> {

    /**
     * 审核文章
     *
     * @param articleIds 文章ID
     * @param status     状态
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:44
     */
    void verifyArticle(ArticleStatus status, Integer... articleIds);

    /**
     * 修改文章推荐级别
     *
     * @param recommend      是否推荐
     * @param articleIds     文章Id
     * @param recommendLevel 推荐级别
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:14
     */
    void recommendArticle(boolean recommend, int recommendLevel, Integer... articleIds);

    /**
     * 修改文章置顶
     *
     * @param articleId 文章Id
     * @param topLevel  置顶级别
     * @param expired 置顶失效期
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:13
     */
    void editArticleTop(int articleId, int topLevel, Date expired);
}
