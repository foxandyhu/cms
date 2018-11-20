package com.jeecms.cms.service;

import java.util.Map;

import com.jeecms.cms.entity.main.Content;

/**
 * ContentListener的抽象实现
 */
public class ContentListenerAbstract implements ContentListener {
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
