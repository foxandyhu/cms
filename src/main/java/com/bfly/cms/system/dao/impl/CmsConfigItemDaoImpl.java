package com.bfly.cms.system.dao.impl;

import com.bfly.cms.system.dao.CmsConfigItemDao;
import com.bfly.cms.system.entity.CmsConfigItem;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:50
 */
@Repository
public class CmsConfigItemDaoImpl extends
        AbstractHibernateBaseDao<CmsConfigItem, Integer> implements CmsConfigItemDao {

    @Override
    public List<CmsConfigItem> getList(Integer configId, Integer category) {
        String sb = "from CmsConfigItem bean where bean.config.id=? and bean.category=?";
        sb=sb.concat(" order by bean.priority asc,bean.id asc");
        return find(sb, configId, category);
    }

    @Override
    public CmsConfigItem findById(Integer id) {
        return get(id);
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