package com.bfly.cms.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.content.dao.CmsModelItemDao;
import com.bfly.cms.content.entity.CmsModelItem;
import com.bfly.cms.content.service.CmsModelItemMng;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.common.hibernate4.Updater;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 11:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsModelItemMngImpl implements CmsModelItemMng {

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			Boolean hasDisabled) {
		return dao.getList(modelId, isChannel, hasDisabled);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public CmsModelItem findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public CmsModelItem save(CmsModelItem bean, Integer modelId) {
		bean.setModel(cmsModelMng.findById(modelId));
		return save(bean);
	}

	@Override
    public CmsModelItem save(CmsModelItem bean) {
		bean.init();
		return dao.save(bean);
	}

	@Override
    public void saveList(List<CmsModelItem> list) {
		for (CmsModelItem item : list) {
			save(item);
		}
	}

	@Override
    public void updatePriority(Integer[] wids, Integer[] priority,
                               String[] label, Boolean[] single, Boolean[] display) {
		CmsModelItem item;
		for (int i = 0, len = wids.length; i < len; i++) {
			item = findById(wids[i]);
			item.setLabel(label[i]);
			item.setPriority(priority[i]);
			item.setSingle(single[i]);
			item.setDisplay(display[i]);
		}
	}

	@Override
    public CmsModelItem update(CmsModelItem bean) {
		Updater<CmsModelItem> updater = new Updater<>(bean);
		CmsModelItem entity = dao.updateByUpdater(updater);
		entity.emptyToNull();
		return entity;
	}

	@Override
    public CmsModelItem deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
    public CmsModelItem[] deleteByIds(Integer[] ids) {
		CmsModelItem[] beans = new CmsModelItem[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemDao dao;

}