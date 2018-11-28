package com.bfly.cms.lucene.dao;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

/**
 * Lucene 数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:51
 */
public interface LuceneContentDao {

    /**
     * 创建索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:52
     */
    Integer index(IndexWriter writer, Integer siteId, Integer channelId,
                  Date startDate, Date endDate, Integer startId, Integer max)
            throws IOException;
}
