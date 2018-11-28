package com.bfly.cms.content.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.CmsModelDao;
import com.bfly.cms.content.entity.CmsModel;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:56
 */
@Repository
public class CmsModelDaoImpl extends AbstractHibernateBaseDao<CmsModel, Integer>
		implements CmsModelDao {

	@Override
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent,Integer siteId) {
		Finder f = Finder.create("select bean from CmsModel bean where "
				+ "(bean.global=true or bean.site.id=:siteId)").setParam("siteId", siteId);
		if (!containDisabled) {
			f.append(" and bean.disabled=false");
		}
		if(hasContent!=null){
			if(hasContent){
				f.append(" and bean.hasContent=true");
			}else{
				f.append(" and bean.hasContent=false");
			}
		}
		f.append(" order by bean.priority");
		return find(f);
	}

	@Override
	public CmsModel getDefModel() {
		String hql = "from CmsModel bean where bean.def=true";
		List<CmsModel> list = getSession().createQuery(hql).setMaxResults(1).list();
		return list.size()>0?list.get(0):null;
	}

	@Override
    public CmsModel findById(Integer id) {
		return get(id);
	}
	
	@Override
	public CmsModel findByPath(String path){
		String hql = "from CmsModel bean where bean.path=:path";
		List<CmsModel> list = getSession().createQuery(hql).setParameter("path", path).setMaxResults(1).list();
		return list.size()>0?list.get(0):null;
	}

	@Override
    public CmsModel save(CmsModel bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsModel deleteById(Integer id) {
		CmsModel entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsModel> getEntityClass() {
		return CmsModel.class;
	}
}