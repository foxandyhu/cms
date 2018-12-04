package com.bfly.cms.comment.dao.impl;

import com.bfly.cms.comment.dao.CmsCommentDao;
import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:16
 */
@Repository
public class CmsCommentDaoImpl extends AbstractHibernateBaseDao<CmsComment, Integer> implements CmsCommentDao {

    @Override
    public Pagination getPage(Integer contentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinder(contentId, null, null, null, greaterThen, checked, recommend, desc, cacheable);
        return find(f, pageNo, pageSize);
    }

    @Override
    public List<CmsComment> getList(Integer contentId, Integer parentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, int count, boolean cacheable) {
        Finder f = getFinder(contentId, parentId, null, null, greaterThen, checked, recommend, desc, cacheable);
        if (first != null) {
            f.setFirstResult(first);
        }
        f.setMaxResults(count);
        return find(f);
    }

    @Override
    public Pagination getPageForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize, boolean cacheable) {
        Finder f = getFinder(contentId, null, toUserId, fromUserId, greaterThen, checked, recommend, desc, cacheable);
        return find(f, pageNo, pageSize);
    }

    @Override
    public List<CmsComment> getListForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, Integer count, boolean cacheable) {
        Finder f = getFinder(contentId, null, toUserId, fromUserId, greaterThen, checked, recommend, desc, cacheable);
        if (first != null) {
            f.setFirstResult(first);
        }
        if (count != null) {
            f.setMaxResults(count);
        }
        return find(f);
    }

    @Override
    public List<CmsComment> getListForDel(Integer userId, Integer commentUserId, String ip) {
        Finder f = Finder.create("from CmsComment bean where 1=1");
        if (commentUserId != null) {
            f.append(" and bean.commentUser.id=:commentUserId");
            f.setParam("commentUserId", commentUserId);
        }
        if (userId != null) {
            f.append(" and bean.content.user.id=:fromUserId");
            f.setParam("fromUserId", userId);
        }
        f.setCacheable(false);
        return find(f);
    }

    private Finder getFinder(Integer contentId, Integer parentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, boolean cacheable) {
        Finder f = Finder.create("from CmsComment bean where 1=1");
        if (parentId != null) {
            f.append(" and bean.parent.id=:parentId");
            f.setParam("parentId", parentId);
        } else if (contentId != null) {
            //按照内容ID来查询对内容的直接评论
            f.append(" and (bean.content.id=:contentId and bean.parent is null )");
            f.setParam("contentId", contentId);
        }
        if (toUserId != null) {
            f.append(" and bean.commentUser.id=:commentUserId");
            f.setParam("commentUserId", toUserId);
        } else if (fromUserId != null) {
            f.append(" and bean.content.user.id=:fromUserId");
            f.setParam("fromUserId", fromUserId);
        }
        if (greaterThen != null) {
            f.append(" and bean.ups>=:greatTo");
            f.setParam("greatTo", greaterThen);
        }
        if (checked != null) {
            f.append(" and bean.checked=:checked");
            f.setParam("checked", checked);
        }
        if (recommend != null) {
            f.append(" and bean.recommend=:recommend");
            f.setParam("recommend", recommend);
        }
        if (desc) {
            f.append(" order by bean.id desc");
        } else {
            f.append(" order by bean.id asc");
        }
        f.setCacheable(cacheable);
        return f;
    }

    @Override
    public CmsComment findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsComment save(CmsComment bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsComment deleteById(Integer id) {
        CmsComment entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    public int deleteByContentId(Integer contentId) {
        String hql = "delete from CmsComment bean where bean.content.id=:contentId";
        return getSession().createQuery(hql).setParameter("contentId", contentId).executeUpdate();
    }

    @Override
    protected Class<CmsComment> getEntityClass() {
        return CmsComment.class;
    }


    @Override
    public Pagination getNewPage(Integer contentId, Short checked, Boolean recommend, int pageNo, int pageSize) {
        String hql = "select c from CmsComment c where c.id in("
                + "select max(bean.id) from CmsComment bean where 1=1 ";
        Finder f = Finder.create(hql);
        f.append(" and bean.replayTime is null");
        if (contentId != null) {
            f.append(" and bean.content.id=" + contentId);
        }
        if (checked != null) {
            f.append(" and bean.checked=" + checked);
        }
        if (recommend != null) {
            f.append(" and bean.recommend=" + recommend);
        }
        f.append(" group by bean.content.id)");
        f.append(" order by c.createTime desc");
        return find(f, pageNo, pageSize);
    }
}