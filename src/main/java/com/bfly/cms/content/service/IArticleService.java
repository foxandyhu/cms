package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Article;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.ArticleStatus;

import java.util.Collection;
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
     * @param expired   置顶失效期
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:13
     */
    void editArticleTop(int articleId, int topLevel, Date expired);

    /**
     * 文章和专题关联
     *
     * @param articleIds 文章ID集合
     * @param topicIds   专题ID集合
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:11
     */
    void saveRelatedSpecialTopic(Collection<? extends Integer> articleIds, Collection<? extends Integer> topicIds);

    /**
     * 删除文章和专题的关联
     *
     * @param articleId 文章ID
     * @param topicId   专题ID
     * @author andy_hulibo@163.com
     * @date 2019/8/8 16:12
     */
    void removeRelatedSpecialTopic(Integer articleId, Integer topicId);

    /**
     * 删除文章图片集
     * @param picId 图片ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:36
     */
    void delArticlePicture(int picId);

    /**
     * 删除文章附件集
     * @param attachmentId 附件ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:37
     */
    void delArticleAttachment(int attachmentId);
}
