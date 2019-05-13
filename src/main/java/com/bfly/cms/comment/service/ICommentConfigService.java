package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.CommentConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * 评论配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:03
 */
public interface ICommentConfigService extends IBaseService<CommentConfig, Integer> {

    /**
     * 获得评论配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:05
     */
    CommentConfig getCommentConfig();
}
