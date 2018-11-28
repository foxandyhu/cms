package com.bfly.cms.system.dao.impl;

import com.bfly.cms.system.dao.ConfigDao;
import com.bfly.cms.system.entity.Config;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:26
 */
@Repository
public class ConfigDaoImpl extends AbstractHibernateBaseDao<Config, String> implements
        ConfigDao {
	
    @Override
    public List<Config> getList() {
        String hql = "from Config";
        return find(hql);
    }

    @Override
    public Config findById(String id) {
        return get(id);
    }

    @Override
    public Config save(Config bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Config deleteById(String id) {
        Config entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Config> getEntityClass() {
        return Config.class;
    }
}