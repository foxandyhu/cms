package com.bfly.cms.channel.dao.impl;

import com.bfly.cms.channel.dao.ChannelDao;
import com.bfly.cms.channel.entity.Channel;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:27
 */
@Repository
public class ChannelDaoImpl extends AbstractHibernateBaseDao<Channel, Integer>
        implements ChannelDao {

    @Override
    public List<Channel> getTopList(Integer siteId, boolean hasContentOnly,
                                    boolean displayOnly, boolean cacheable) {
        Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
        return find(f);
    }

    @Override
    public Pagination getTopPage(Integer siteId, boolean hasContentOnly,
                                 boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
        Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
        return find(f, pageNo, pageSize);
    }

    private Finder getTopFinder(Integer siteId, boolean hasContentOnly,
                                boolean displayOnly, boolean cacheable) {
        Finder f = Finder.create("from Channel bean");
        f.append(" where bean.site.id=:siteId and bean.parent.id is null");
        f.setParam("siteId", siteId);
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        if (displayOnly) {
            f.append(" and bean.display=true");
        }
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(cacheable);
        return f;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Channel> getTopListByRigth(Integer userId, Integer siteId,
                                           boolean hasContentOnly) {
        Finder f = Finder.create("select bean from Channel bean");
        f.append(" join bean.users user");
        f.append(" where user.id=:userId and bean.site.id=:siteId");
        f.setParam("userId", userId).setParam("siteId", siteId);
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        f.append(" and bean.parent.id is null");
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(true);
        return find(f);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Channel> getChildList(Integer parentId, boolean hasContentOnly,
                                      boolean displayOnly, boolean cacheable) {
        Finder f = getChildFinder(parentId, hasContentOnly, displayOnly,
                cacheable);
        return find(f);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Channel> getBottomList(Integer siteId, boolean hasContentOnly) {
        Finder f = Finder.create("select  bean from Channel bean");
        f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        f.append(" and size(bean.child) <= 0");
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public Pagination getChildPage(Integer parentId, boolean hasContentOnly,
                                   boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
        Finder f = getChildFinder(parentId, hasContentOnly, displayOnly,
                cacheable);
        return find(f, pageNo, pageSize);
    }

    private Finder getChildFinder(Integer parentId, boolean hasContentOnly,
                                  boolean displayOnly, boolean cacheable) {
        Finder f = Finder.create("from Channel bean");
        f.append(" where bean.parent.id=:parentId");
        f.setParam("parentId", parentId);
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        if (displayOnly) {
            f.append(" and bean.display=true");
        }
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(true);
        return f;
    }

    @Override
    public List<Channel> getChildListByRight(Integer userId, Integer parentId,
                                             boolean hasContentOnly) {
        Finder f = Finder.create("select bean from Channel bean");
        f.append(" join bean.users user");
        f.append(" where user.id=:userId and bean.parent.id=:parentId");
        f.setParam("userId", userId).setParam("parentId", parentId);
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(true);
        return find(f);
    }


    @Override
    public Channel findById(Integer id) {
        Channel entity = get(id);
        return entity;
    }

    @Override
    public Channel findByPath(String path, Integer siteId, boolean cacheable) {
        String hql = "from Channel bean where bean.path=? and bean.site.id=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, path).setParameter(1, siteId);
        // 做一些容错处理，因为毕竟没有在数据库中限定path是唯一的。
        query.setMaxResults(1);
        return (Channel) query.setCacheable(cacheable).uniqueResult();
    }

    @Override
    public Channel save(Channel bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public Channel deleteById(Integer id) {
        Channel entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<Channel> getEntityClass() {
        return Channel.class;
    }
}