package com.bfly.cms.words.service;

/**
 * 搜索词缓存接口
 */
public interface SearchWordsCache {

    /**
     * 搜索词汇缓存
     *
     * @param name
     */
    void cacheWord(String name);
}
