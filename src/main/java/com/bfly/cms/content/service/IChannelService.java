package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Channel;
import com.bfly.core.base.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 栏目Service
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:09
 */
public interface IChannelService extends IBaseService<Channel, Integer> {

    /**
     * 栏目排序
     *
     * @param downId 下移Id
     * @param upId   上移ID
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:21
     */
    void sortChannel(int upId, int downId);

    /**
     * 获得所有的栏目信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 14:54
     */
    List<Map<String, Object>> getAllChannel();

    /**
     * 根据栏目路径查询栏目对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:43
     */
    Channel getChannelByPath(String path);
}
