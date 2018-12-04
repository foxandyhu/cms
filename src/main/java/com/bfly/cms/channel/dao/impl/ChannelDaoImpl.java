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
public class ChannelDaoImpl extends AbstractHibernateBaseDao<Channel, Integer> implements ChannelDao {

    @Override
    public List<Channel> getTopList(boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
        Finder f = getTopFinder(hasContentOnly, displayOnly, cacheable);
        return find(f);
    }

    @Override
    public Pagination getTopPage(boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
        Finder f = getTopFinder(hasContentOnly, displayOnly, cacheable);
        return find(f, pageNo, pageSize);
    }

    private Finder getTopFinder(boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
        Finder f = Finder.create("from Channel bean");
        f.append(" where bean.parent.id is null");
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
    public List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly) {
        Finder f = getChildFinder(parentId, hasContentOnly, displayOnly);
        return find(f);
    }

    @Override
    public List<Channel> getBottomList(boolean hasContentOnly) {
        Finder f = Finder.create("select  bean from Channel bean");
        f.append(" where bean.site.id=:siteId");
        if (hasContentOnly) {
            f.append(" and bean.hasContent=true");
        }
        f.append(" and size(bean.child) <= 0");
        f.append(" order by bean.priority asc,bean.id asc");
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, int pageNo, int pageSize) {
        Finder f = getChildFinder(parentId, hasContentOnly, displayOnly);
        return find(f, pageNo, pageSize);
    }

    private Finder getChildFinder(Integer parentId, boolean hasContentOnly, boolean displayOnly) {
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
    public Channel findById(Integer id) {
        return get(id);
    }

    @Override
    public Channel findByPath(String path, boolean cacheable) {
        String hql = "from Channel bean where bean.path=?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, path);
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