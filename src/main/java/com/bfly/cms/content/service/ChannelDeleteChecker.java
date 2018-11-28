package com.bfly.cms.content.service;

/**
 * 检查章节是否可以删除的接口
 */
public interface ChannelDeleteChecker {
    /**
     * 如不能删除，则返回国际化提示信息；否则返回null。
     */
    String checkForChannelDelete(Integer channelId);
}
