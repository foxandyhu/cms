package com.bfly.cms.manager.assist.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsGuestbookDao;
import com.bfly.cms.entity.assist.CmsGuestbook;
import com.bfly.cms.entity.assist.CmsGuestbookExt;
import com.bfly.cms.manager.assist.CmsGuestbookCtgMng;
import com.bfly.cms.manager.assist.CmsGuestbookExtMng;
import com.bfly.cms.manager.assist.CmsGuestbookMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;
import com.bfly.core.manager.CmsSiteMng;
import com.bfly.core.manager.CmsUserExtMng;

@Service
@Transactional
public class CmsGuestbookMngImpl implements CmsGuestbookMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer ctgId,Integer ctgIds[],
			Integer userId, Boolean recommend,Short checked,
			boolean desc, boolean cacheable, int pageNo,int pageSize) {
		return dao.getPage(siteId, ctgId,ctgIds,userId, recommend, checked, desc, cacheable,
				pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true)
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
			Integer userId,Boolean recommend, Short checked, boolean desc,
			boolean cacheable, int first, int max) {
		return dao.getList(siteId, ctgId,userId, recommend, checked, desc, cacheable,
				first, max);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsGuestbook findById(Integer id) {
		CmsGuestbook entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext,
                             Integer ctgId, String ip) {
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		bean.setIp(ip);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.init();
		dao.save(bean);
		cmsGuestbookExtMng.save(ext, bean);
		return bean;
	}

	@Override
    public CmsGuestbook save(CmsUser member, Integer siteId, Integer ctgId,
                             String ip, String title, String content, String email,
                             String phone, String qq) {
		CmsGuestbook guestbook = new CmsGuestbook();
		guestbook.setMember(member);
		guestbook.setSite(cmsSiteMng.findById(siteId));
		guestbook.setIp(ip);
		CmsGuestbookExt ext = new CmsGuestbookExt();
		ext.setTitle(title);
		ext.setContent(content);
		ext.setEmail(email);
		ext.setPhone(phone);
		ext.setQq(qq);
		//累计今日留言数
		if(member!=null){
			CmsUserExt userExt=member.getUserExt();
			userExt.setTodayGuestbookTotal(userExt.getTodayGuestbookTotal()+1);
			userExtMng.update(userExt, member);
		}
		return save(guestbook, ext, ctgId, ip);
	}

	@Override
    public CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext,
                               Integer ctgId) {
		Updater<CmsGuestbook> updater = new Updater<CmsGuestbook>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		cmsGuestbookExtMng.update(ext);
		return bean;
	}

	@Override
    public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsGuestbook[] deleteByIds(Integer[] ids) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	@Override
    public CmsGuestbook[] checkByIds(Integer[] ids, CmsUser checkUser, Short checkStatus) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = checkById(ids[i],checkUser,checkStatus);
		}
		return beans;
	}
	
	private CmsGuestbook checkById(Integer id,CmsUser checkUser,Short checkStatus){
		CmsGuestbook bean=findById(id);
		Updater<CmsGuestbook> updater = new Updater<CmsGuestbook>(bean);
		bean = dao.updateByUpdater(updater);
		if(checkStatus!=null && !checkStatus.equals(0)){
			bean.setAdmin(checkUser);
		}
		bean.setChecked(checkStatus);
		return bean;
	}


	@Autowired
	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	@Autowired
	private CmsGuestbookExtMng cmsGuestbookExtMng;
	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsGuestbookDao dao;
	@Autowired
	private CmsUserExtMng userExtMng;
}