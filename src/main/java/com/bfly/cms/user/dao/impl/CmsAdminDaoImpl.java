package com.bfly.cms.user.dao.impl;

import com.bfly.cms.user.dao.CmsAdminDao;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:02
 */
@Repository
public class CmsAdminDaoImpl extends AbstractHibernateBaseDao<CmsAdmin, Integer> implements CmsAdminDao {

    @Override
    public CmsAdmin findByUsername(String username) {
        return findUniqueByProperty("username", username);
    }

    @Override
    public CmsAdmin findById(Integer id) {
        return get(id);
    }

    @Override
    public void updateLoginInfo(CmsAdmin admin) {
        getSession().update(admin);
    }

    @Override
    protected Class<CmsAdmin> getEntityClass() {
        return CmsAdmin.class;
    }
}