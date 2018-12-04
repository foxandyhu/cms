package com.bfly.cms.acquisition.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.acquisition.dao.CmsAcquisitionTempDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;
import com.bfly.core.base.dao.impl.Finder;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/12/4 10:03
 */
@Repository
public class CmsAcquisitionTempDaoImpl extends AbstractHibernateBaseDao<CmsAcquisitionTemp, Integer> implements CmsAcquisitionTempDao {

	@Override
	public List<CmsAcquisitionTemp> getList() {
		Finder f = Finder.create("from CmsAcquisitionTemp bean where 1=1");
		f.append(" order by bean.id desc");
		return find(f);
	}

	@Override
    public CmsAcquisitionTemp findById(Integer id) {
		return  get(id);
	}

	@Override
    public CmsAcquisitionTemp save(CmsAcquisitionTemp bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsAcquisitionTemp deleteById(Integer id) {
		CmsAcquisitionTemp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public Integer getPercent() {
		Query query = getSession().createQuery("select max(percent) from CmsAcquisitionTemp");
		return (Integer) (query.uniqueResult() == null ? 0 : query.uniqueResult());
	}

	@Override
    public void clear(String channelUrl) {
		StringBuilder sb = new StringBuilder(100);
		Map<String, Object> params = new HashMap<>(2);
		sb.append("delete from CmsAcquisitionTemp");
		if (StringUtils.isNotBlank(channelUrl)) {
			sb.append(" and channelUrl!=:channelUrl");
			params.put("channelUrl", channelUrl);
		}
		Query query = getSession().createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.executeUpdate();
	}

	@Override
	protected Class<CmsAcquisitionTemp> getEntityClass() {
		return CmsAcquisitionTemp.class;
	}

}