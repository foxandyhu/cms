package com.jeecms.cms.dao.main.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.main.CmsModelItemDao;
import com.jeecms.cms.entity.main.CmsModelItem;

@Repository
public class CmsModelItemDaoImpl extends
        AbstractHibernateBaseDao<CmsModelItem, Integer> implements CmsModelItemDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			Boolean hasDisabled) {
		StringBuilder sb = new StringBuilder(
				"from CmsModelItem bean where bean.model.id=? and bean.channel=?");
		if (hasDisabled!=null) {
			if(!hasDisabled){
				sb.append(" and bean.display=true");
			}else{
				sb.append(" and bean.display=false");
			}
		}
		sb.append(" order by bean.priority asc,bean.id asc");
		return find(sb.toString(), modelId, isChannel);
	}

	@Override
    public CmsModelItem findById(Integer id) {
		CmsModelItem entity = get(id);
		return entity;
	}

	@Override
    public CmsModelItem save(CmsModelItem bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsModelItem deleteById(Integer id) {
		CmsModelItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsModelItem> getEntityClass() {
		return CmsModelItem.class;
	}
}