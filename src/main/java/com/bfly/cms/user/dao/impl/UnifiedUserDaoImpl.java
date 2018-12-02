package com.bfly.cms.user.dao.impl;

import com.bfly.cms.user.dao.UnifiedUserDao;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/2 17:17
 */
@Repository
public class UnifiedUserDaoImpl extends AbstractHibernateBaseDao<UnifiedUser, Integer> implements UnifiedUserDao {

    @Override
    public UnifiedUser getByUsername(String username) {
        return findUniqueByProperty("username", username);
    }

    @Override
    public int countByEmail(String email) {
        String hql = "select count(*) from UnifiedUser bean where bean.email=:email";
        Query query = getSession().createQuery(hql);
        query.setParameter("email", email);
        return ((Number) query.iterate().next()).intValue();
    }

    @Override
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria();
        return findByCriteria(crit, pageNo, pageSize);
    }

    @Override
    public UnifiedUser findById(Integer id) {
        return get(id);
    }

    @Override
    public UnifiedUser save(UnifiedUser bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public UnifiedUser deleteById(Integer id) {
        UnifiedUser entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<UnifiedUser> getEntityClass() {
        return UnifiedUser.class;
    }
}