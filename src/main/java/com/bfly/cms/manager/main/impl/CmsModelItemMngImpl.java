package com.bfly.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.main.CmsModelItemDao;
import com.bfly.cms.entity.main.CmsModelItem;
import com.bfly.cms.manager.main.CmsModelItemMng;
import com.bfly.cms.manager.main.CmsModelMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsModelItemMngImpl implements CmsModelItemMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			Boolean hasDisabled) {
		return dao.getList(modelId, isChannel, hasDisabled);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsModelItem findById(Integer id) {
		CmsModelItem entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsModelItem save(CmsModelItem bean, Integer modelId) {
		bean.setModel(cmsModelMng.findById(modelId));
		return save(bean);
	}

	@Override
    public CmsModelItem save(CmsModelItem bean) {
		bean.init();
		dao.save(bean);
		return bean;
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
		Updater<CmsModelItem> updater = new Updater<CmsModelItem>(bean);
		CmsModelItem entity = dao.updateByUpdater(updater);
		entity.emptyToNull();
		return entity;
	}

	@Override
    public CmsModelItem deleteById(Integer id) {
		CmsModelItem bean = dao.deleteById(id);
		return bean;
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