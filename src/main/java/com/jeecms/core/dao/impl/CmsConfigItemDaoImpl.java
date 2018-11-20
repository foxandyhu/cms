package com.jeecms.core.dao.impl;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.core.dao.CmsConfigItemDao;
import com.jeecms.core.entity.CmsConfigItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsConfigItemDaoImpl extends
        AbstractHibernateBaseDao<CmsConfigItem, Integer> implements CmsConfigItemDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<CmsConfigItem> getList(Integer configId, Integer category) {
        StringBuilder sb = new StringBuilder(
                "from CmsConfigItem bean where bean.config.id=? and bean.category=?");
        sb.append(" order by bean.priority asc,bean.id asc");
        return find(sb.toString(), configId, category);
    }

    @Override
    public CmsConfigItem findById(Integer id) {
        CmsConfigItem entity = get(id);
        return entity;
    }

    @Override
    public CmsConfigItem save(CmsConfigItem bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsConfigItem deleteById(Integer id) {
        CmsConfigItem entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsConfigItem> getEntityClass() {
        return CmsConfigItem.class;
    }
}