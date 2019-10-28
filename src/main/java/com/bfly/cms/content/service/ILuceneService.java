package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.common.page.Pager;

/**
 * Lucene索引服务
 *
 * @author andy_hulibo@163.com
 * @date 2019/10/25 11:00
 */
public interface ILuceneService {

    /**
     * 给文章创建索引
     *
     * @param articles 索引的文章
     * @return 成功失败
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:04
     */
    boolean createIndex(Article... articles);

    /**
     * 文章创建索引
     *
     * @param articles
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/10/28 12:12
     */
    boolean createIndex(ArticleLuceneDTO... articles);

    /**
     * 更新文章索引
     *
     * @param articles 文章
     * @return 成功失败
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:04
     */
    boolean updateIndex(Article... articles);

    /**
     * 删除索引
     *
     * @param articleId 文章ID
     * @return 成功失败
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:06
     */
    boolean deleteIndex(Integer... articleId);

    /**
     * 删除所有索引库
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 14:09
     */
    boolean deleteAll();

    /**
     * 搜索索引
     *
     * @param keyWord 关键字
     * @return 文章分页对象
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:08
     */
    Pager<ArticleLuceneDTO> query(String keyWord);

}
