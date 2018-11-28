package com.bfly.cms.user.dao.impl;

import com.bfly.cms.user.dao.CmsUserMenuDao;
import com.bfly.cms.user.entity.CmsUserMenu;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsUserMenuDaoImpl extends AbstractHibernateBaseDao<CmsUserMenu, Integer> implements CmsUserMenuDao {
    @Override
    public Pagination getPage(Integer userId, int pageNo, int pageSize) {
        String hql = "from CmsUserMenu menu ";
        Finder f = Finder.create(hql);
        if (userId != null) {
            f.append(" where menu.user.id=:userId").setParam("userId", userId);
        }
        f.append(" order by menu.priority asc");
        return find(f, pageNo, pageSize);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CmsUserMenu> getList(Integer userId, int count) {
        String hql = "from CmsUserMenu menu ";
        Finder f = Finder.create(hql);
        if (userId != null) {
            f.append(" where menu.user.id=:userId").setParam("userId", userId);
        }
        f.append(" order by menu.priority asc");
        f.setFirstResult(0);
        f.setMaxResults(count);
        return find(f);
    }

    @Override
    public CmsUserMenu findById(Integer id) {
        CmsUserMenu entity = get(id);
        return entity;
    }

    @Override
    public CmsUserMenu save(CmsUserMenu bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsUserMenu deleteById(Integer id) {
        CmsUserMenu entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsUserMenu> getEntityClass() {
        return CmsUserMenu.class;
    }
}