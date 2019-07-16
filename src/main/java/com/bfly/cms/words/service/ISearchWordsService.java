package com.bfly.cms.words.service;

import com.bfly.cms.words.entity.SearchWords;
import com.bfly.core.base.service.IBaseService;

/**
 * 标签业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:43
 */
public interface ISearchWordsService extends IBaseService<SearchWords, Integer> {

    /**
     * 更新热词推荐状态
     * @author andy_hulibo@163.com
     * @date 2019/7/15 15:24
     */
    boolean editRecommend(int searchId,boolean status);
}
