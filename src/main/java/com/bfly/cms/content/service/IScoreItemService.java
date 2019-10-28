package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.ScoreItem;
import com.bfly.core.base.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 评分项业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:53
 */
public interface IScoreItemService extends IBaseService<ScoreItem, Integer> {

    /**
     * 根据评分组删除评分项
     * @param groupId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/7/15 21:01
     */
    int removeScoreItem(int groupId);

    /**
     * 栏目排序
     *
     * @param upId   上移ID
     * @param downId 下移ID
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:21
     */
    void sortScoreItem(int upId, int downId);

    /**
     * 获得文章的评分项
     * @param articleId
     * @param scoreGroupId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/8 18:26
     */
    List<Map<String,Object>> getArticleScoreItems(int articleId, int scoreGroupId);
}
