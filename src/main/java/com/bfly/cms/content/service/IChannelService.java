package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.Model;
import com.bfly.core.base.service.IBaseService;

/**
 * 栏目Service
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:09
 */
public interface IChannelService extends IBaseService<Channel, Integer> {

    /**
     * 栏目排序
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:21
     */
    void sortChannel(int upChannelId,int downChannelId);
}
