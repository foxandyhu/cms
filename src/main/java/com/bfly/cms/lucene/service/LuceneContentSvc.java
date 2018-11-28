package com.bfly.cms.lucene.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.common.page.Pagination;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Lucene 业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:46
 */
public interface LuceneContentSvc {

    /**
     * 创建索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:49
     */
    Integer createIndex(Integer siteId, Integer channelId,
                        Date startDate, Date endDate, Integer startId, Integer max)
            throws IOException, ParseException;

    /**
     * 创建索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:49
     */
    Integer createIndex(Integer siteId, Integer channelId,
                        Date startDate, Date endDate, Integer startId, Integer max,
                        Directory dir) throws IOException, ParseException;

    /**
     * 创建索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:49
     */
    void createIndex(Content content, Directory dir) throws IOException;

    /**
     * 创建索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    void createIndex(Content content) throws IOException;

    /**
     * 删除索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    void deleteIndex(Integer contentId) throws IOException,
            ParseException;

    /**
     * 删除索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    void deleteIndex(Integer contentId, Directory dir)
            throws IOException, ParseException;

    /**
     * 更新索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    void updateIndex(Content content) throws IOException, ParseException;

    /**
     * 更新索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    void updateIndex(Content content, Directory dir) throws IOException,
            ParseException;

    /**
     * 搜索
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    Pagination searchPage(String path, String queryString, String category, String workplace,
                          Integer siteId, Integer channelId, Date startDate, Date endDate,
                          int pageNo, int pageSize) throws
            IOException, ParseException;

    /**
     * 搜索
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    Pagination searchPage(Directory dir, String queryString, String category, String workplace,
                          Integer siteId, Integer channelId, Date startDate, Date endDate,
                          int pageNo, int pageSize) throws IOException, ParseException;

    /**
     * 搜索
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:48
     */
    List<Content> searchList(String path, String queryString, String category, String workplace,
                             Integer siteId, Integer channelId, Date startDate, Date endDate,
                             int pageNo, int pageSize) throws IOException, ParseException;

    /**
     * 搜索
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:46
     */
    List<Content> searchList(Directory dir, String queryString, String category, String workplace,
                             Integer siteId, Integer channelId, Date startDate, Date endDate,
                             int first, int max) throws IOException, ParseException;

}
