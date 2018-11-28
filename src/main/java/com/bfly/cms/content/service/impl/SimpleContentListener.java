package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.service.ContentListener;
import com.bfly.cms.content.entity.Content;

import java.util.Map;

/**
 * ContentListener的抽象实现
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:55
 */
public class SimpleContentListener implements ContentListener {
    @Override
    public void afterChange(Content content, Map<String, Object> map) {
    }

    @Override
    public void afterDelete(Content content) {
    }

    @Override
    public void afterSave(Content content) {
    }

    @Override
    public Map<String, Object> preChange(Content content) {
        return null;
    }

    @Override
    public void preDelete(Content content) {
    }

    @Override
    public void preSave(Content content) {
    }
}
