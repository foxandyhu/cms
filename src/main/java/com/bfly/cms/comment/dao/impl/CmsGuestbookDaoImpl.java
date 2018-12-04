package com.bfly.cms.comment.dao.impl;

import com.bfly.cms.comment.dao.CmsGuestbookDao;
import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:46
 */
@Repository
public class CmsGuestbookDaoImpl extends AbstractHibernateBaseDao<CmsGuestbook, Integer> implements CmsGuestbookDao {

	@Override
    public Pagination getPage(Integer ctgId, Integer ctgIds[], Integer userId, Boolean recommend, Short checked, boolean asc, boolean cacheable, int pageNo, int pageSize) {
		Finder f = createFinder(ctgId, ctgIds,userId,recommend, checked, asc, cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<CmsGuestbook> getList(Integer ctgId, Integer userId,Boolean recommend, Short checked, boolean desc, boolean cacheable, int first, int max) {
		Finder f = createFinder( ctgId, null,userId,recommend, checked, desc, cacheable);
		f.setFirstResult(first);
		f.setMaxResults(max);
		return find(f);
	}

	private Finder createFinder(Integer ctgId,Integer ctgIds[],Integer userId,
			Boolean recommend, Short checked, boolean desc, boolean cacheable) {
		Finder f = Finder.create("from CmsGuestbook bean where 1=1");
		if (ctgId != null) {
			f.append(" and bean.ctg.id =:ctgId");
			f.setParam("ctgId", ctgId);
		}
		if(ctgIds!=null&&ctgIds.length>0){
			f.append(" and bean.ctg.id in(:ctgIds)");
			f.setParamList("ctgIds", ctgIds);
		}
		if (userId != null) {
			f.append(" and bean.member.id=:userId");
			f.setParam("userId", userId);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		if (checked != null) {
			f.append(" and bean.checked=:checked");
			f.setParam("checked", checked);
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
    public CmsGuestbook findById(Integer id) {
		return get(id);
	}

	@Override
    public CmsGuestbook save(CmsGuestbook bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGuestbook> getEntityClass() {
		return CmsGuestbook.class;
	}
}