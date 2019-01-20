package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.GuestBookConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * 留言配置业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:03
 */
public interface IGuestBookConfigService extends IBaseService<GuestBookConfig, Integer> {

    /**
     * 获得留言配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:05
     */
    GuestBookConfig getGuestBookConfig();
}
