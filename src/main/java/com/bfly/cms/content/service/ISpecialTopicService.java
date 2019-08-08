package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.SpecialTopic;
import com.bfly.core.base.service.IBaseService;

import java.util.List;

/**
 * 栏目Service
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:09
 */
public interface ISpecialTopicService extends IBaseService<SpecialTopic, Integer> {

    /**
     * 栏目专题
     *
     * @param downId 下移的专题ID
     * @param uplId  上移的专题ID
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:21
     */
    void sortSpecialTopic(int uplId, int downId);

    /**
     * 获得文章对应的专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 20:06
     */
    List<SpecialTopic> getSpecialTopicForArticle(int articleId);
}
