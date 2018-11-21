package com.bfly.cms.service.impl;

import com.bfly.cms.entity.main.Content;
import com.bfly.cms.service.ContentListener;

import java.util.Map;

/**
 * ContentListener的抽象实现
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
