package com.bfly.cms.siteconfig.dao.impl;

import com.bfly.cms.siteconfig.dao.FtpDao;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 14:36
 */
@Repository
public class FtpDaoImpl extends AbstractHibernateBaseDao<Ftp, Integer> implements
        FtpDao {

    @Override
    public List<Ftp> getList() {
        String hql = "from Ftp bean";
        return find(hql);
    }

    @Override
    public Ftp findById(Integer id) {
        return get(id);
    }

    @Override
    public Ftp save(Ftp bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Ftp deleteById(Integer id) {
        Ftp entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Ftp> getEntityClass() {
        return Ftp.class;
    }
}