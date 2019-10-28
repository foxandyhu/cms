package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.ArticleStatus;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
     *
     * @param picId 图片ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:36
     */
    void delArticlePicture(int picId);

    /**
     * 删除文章附件集
     *
     * @param attachmentId 附件ID
     * @author andy_hulibo@163.com
     * @date 2019/8/13 10:37
     */
    void delArticleAttachment(int attachmentId);

    /**
     * 获得今日发布的文章总数和总文章数
     *
     * @return Map对象 total 文章总数  today 今日文章数
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:18
     */
    Map<String, BigInteger> getTodayAndTotalArticle();

    /**
     * 点击率头几名的文章信息
     *
     * @param limit
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:46
     */
    List<Map<String, Object>> getClickTopArticle(int limit);

    /**
     * 评论数头几名的文章信息
     *
     * @param limit
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:47
     */
    List<Map<String, Object>> getCommentsTopArticle(int limit);

    /**
     * 下一篇文章
     *
     * @param currentArticleId 当前文章ID
     * @param channelId        栏目ID
     * @return Map              部分字段
     * @author andy_hulibo@163.com
     * @date 2019/9/4 22:54
     */
    Map<String, Object> getNext(int currentArticleId, Integer channelId);

    /**
     * 上一篇文章
     *
     * @param currentArticleId 当前文章ID
     * @param channelId        栏目ID
     * @return Map              部分字段
     * @author andy_hulibo@163.com
     * @date 2019/9/4 22:54
     */
    Map<String, Object> getPrev(int currentArticleId, Integer channelId);

    /**
     * 修改文章评论数
     *
     * @param articleId 文章ID
     * @param setup     新增一条评论1 删除一条评论-1
     * @author andy_hulibo@163.com
     * @date 2019/9/6 16:40
     */
    void incrementArticleComments(int articleId, int setup);

    /**
     * 修改文章浏览数
     *
     * @param articleId
     * @param setup
     * @author andy_hulibo@163.com
     * @date 2019/9/6 17:46
     */
    void incrementArticleViews(int articleId, int setup);

    /**
     * 修改下载量
     *
     * @param articleId
     * @param setup
     * @author andy_hulibo@163.com
     * @date 2019/9/7 18:36
     */
    void incrementArticleDownloads(int articleId, int setup);

    /**
     * 文章评分
     *
     * @param articleId
     * @param scoreItemId
     * @author andy_hulibo@163.com
     * @date 2019/9/8 16:25
     */
    void incrementArticleScores(int articleId, int scoreItemId);

    /**
     * 文章顶和踩
     *
     * @param articleId 评论ID
     * @param isUp      顶或踩
     * @author andy_hulibo@163.com
     * @date 2019/9/8 13:50
     */
    void incrementArticleUpDowns(int articleId, boolean isUp);

    /**
     * 重置置顶过期的文章置顶级别为0
     *
     * @param date
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/14 11:26
     */
    int resetArticleTopLevelForExpired(Date date);

    /**
     * 自动发布文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 13:17
     */
    int autoPublishArticle();

    /**
     * 重新生成文章索引库
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 14:08
     */
    void resetArticleIndex();

    /**
     * 索引库中搜索
     *
     * @param keyWord
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/10/25 17:46
     */
    Pager<ArticleLuceneDTO> searchFromIndex(String keyWord);
}
