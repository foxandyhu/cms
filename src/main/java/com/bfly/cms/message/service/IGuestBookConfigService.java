package com.bfly.cms.message.service;

import com.bfly.cms.message.entity.GuestBookConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * @author andy_hulibo@163.com
 * @date 2019/9/15 13:28
 */
public interface IGuestBookConfigService extends IBaseService<GuestBookConfig, Integer> {

    /**
     * 得到留言配置
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:28
     */
    GuestBookConfig getConfig();
}
