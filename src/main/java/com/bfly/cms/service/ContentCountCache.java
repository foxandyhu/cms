package com.bfly.cms.service;

/**
 * 内容计数器缓存接口
 */
public interface ContentCountCache {

    /**
     * 浏览一次
     *
     * @param id 内容ID
     * @return 返回浏览次数，评论次数，顶次数，踩次数。
     */
    int[] viewAndGet(Integer id);
}
