package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;

import java.util.Map;

/**
 * 内容修改监听器
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:54
 */
public interface ContentListener {
    /**
     * 保存之前执行
     *
     * @param content
     */
    void preSave(Content content);

    /**
     * 保存之后执行
     *
     * @param content
     */
    void afterSave(Content content);

    /**
     * 修改之前执行
     *
     * @param content 修改前的Content
     * @return 获取一些需要在afterChange中使用的值。
     */
    Map<String, Object> preChange(Content content);

    /**
     * 修改之后执行
     *
     * @param content 修改后的Content
     * @param map     从{@link #preChange(Content)}方法返回的值。
     */
    void afterChange(Content content, Map<String, Object> map);

    /**
     * 删除之前执行
     *
     * @param content
     */
    void preDelete(Content content);

    /**
     * 删除之后执行
     *
     * @param content
     */
    void afterDelete(Content content);
}
