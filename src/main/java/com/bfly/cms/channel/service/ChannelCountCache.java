package com.bfly.cms.channel.service;

/**
 * 栏目计数器缓存接口
 */
public interface ChannelCountCache {

    /**
     * 浏览一次
     *
     * @param id 栏目ID
     * @return 返回浏览次数。
     */
    int[] viewAndGet(Integer id);
}
