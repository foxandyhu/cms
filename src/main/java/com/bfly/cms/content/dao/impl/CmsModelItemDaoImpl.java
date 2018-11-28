package com.bfly.cms.content.dao.impl;

import java.util.List;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.CmsModelItemDao;
import com.bfly.cms.content.entity.CmsModelItem;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 11:39
 */
@Repository
public class CmsModelItemDaoImpl extends
        AbstractHibernateBaseDao<CmsModelItem, Integer> implements CmsModelItemDao {

	@Override
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
		return get(id);
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