package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.CmsCommentDao;
import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.cms.comment.service.CmsCommentExtMng;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.content.service.ContentCountMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.words.service.CmsSensitivityMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsCommentMngImpl implements CmsCommentMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(Integer contentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize) {
        Pagination page = dao.getPage(contentId, greaterThen, checked, recommend, desc, pageNo, pageSize, false);
        return page;
    }

    @Override
    public Pagination getNewPage(Integer contentId, Short checked, Boolean recommend, int pageNo, int pageSize) {
        return dao.getNewPage(contentId, checked, recommend, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPageForTag(Integer contentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize) {
        Pagination page = dao.getPage(contentId, greaterThen, checked, recommend, desc, pageNo, pageSize, true);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPageForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, int pageNo, int pageSize) {
        Pagination page = dao.getPageForMember(contentId, toUserId, fromUserId, greaterThen, checked, recommend, desc, pageNo, pageSize, false);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsComment> getListForMember(Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, Integer count) {
        return dao.getListForMember(contentId, toUserId, fromUserId, greaterThen, checked, recommend, desc, first, count, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsComment> getListForDel(Integer userId, Integer commentUserId, String ip) {
        return dao.getListForDel(userId, commentUserId, ip);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsComment> getListForTag(Integer contentId, Integer parentId, Integer greaterThen, Short checked, Boolean recommend, boolean desc, Integer first, int count) {
        return dao.getList(contentId, parentId, greaterThen, checked, recommend, desc, first, count, true);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsComment findById(Integer id) {
        CmsComment entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsComment comment(Integer score, String text, String ip, Integer contentId, Integer userId, short checked, boolean recommend, Integer parentId) {
        CmsComment comment = new CmsComment();
        comment.setContent(contentMng.findById(contentId));
        if (userId != null) {
            CmsUser user = cmsUserMng.findById(userId);
            comment.setCommentUser(user);
            //累计今日留言数
            if (user != null) {
                CmsUserExt userExt = user.getUserExt();
                userExt.setTodayCommentTotal(userExt.getTodayCommentTotal() + 1);
                userExtMng.update(userExt, user);
            }
        }
        comment.setChecked(checked);
        comment.setRecommend(recommend);
        comment.setScore(score);
        comment.init();
        if (parentId != null) {
            CmsComment parent = findById(parentId);
            if (parent != null) {
                comment.setParent(parent);
                update(parent, parent.getCommentExt());
            }
        }
        dao.save(comment);
        text = cmsSensitivityMng.replaceSensitivity(text);
        cmsCommentExtMng.save(ip, text, comment);
        contentCountMng.commentCount(contentId);
        return comment;
    }

    @Override
    public CmsComment update(CmsComment bean, CmsCommentExt ext) {
        Updater<CmsComment> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        cmsCommentExtMng.update(ext);
        return bean;
    }

    @Override
    public int deleteByContentId(Integer contentId) {
        cmsCommentExtMng.deleteByContentId(contentId);
        return dao.deleteByContentId(contentId);
    }

    @Override
    public CmsComment deleteById(Integer id) {
        CmsComment bean = dao.deleteById(id);
        CmsComment parent = bean.getParent();
        if (parent != null && bean.getChecked() == 1) {
            if (parent.getReplyCount() != null && parent.getReplyCount() > 0) {
                parent.setReplyCount(parent.getReplyCount() - 1);
            } else {
                parent.setReplyCount(0);
            }
            update(parent, parent.getCommentExt());
        }
        bean.getChild().clear();
        return bean;
    }

    @Override
    public CmsComment[] deleteByIds(Integer[] ids) {
        CmsComment[] beans = new CmsComment[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public CmsComment[] checkByIds(Integer[] ids, short checked) {
        CmsComment[] beans = new CmsComment[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = checkById(ids[i], checked);
        }
        return beans;
    }


    private CmsComment checkById(Integer id, short checked) {
        CmsComment bean = findById(id);
        CmsComment parent = bean.getParent();
        if (parent != null) {
            //从未审核/审核不通过到审核审核通过
            if ((bean.getChecked() == 0 || bean.getChecked() == 2) && checked == 1) {
                if (parent.getReplyCount() != null && parent.getReplyCount() > 0) {
                    parent.setReplyCount(parent.getReplyCount() + 1);
                } else {
                    parent.setReplyCount(1);
                }
            }
            //从审核通过到审核不通过
            if (bean.getChecked() == 1 && checked == 2) {
                if (parent.getReplyCount() != null && parent.getReplyCount() > 0) {
                    parent.setReplyCount(parent.getReplyCount() - 1);
                } else {
                    parent.setReplyCount(0);
                }
            }
            update(parent, parent.getCommentExt());
        }
        Updater<CmsComment> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        bean.setChecked(checked);
        return bean;
    }

    @Override
    public void ups(Integer id) {
        CmsComment comment = findById(id);
        comment.setUps((short) (comment.getUps() + 1));
    }

    @Override
    public void downs(Integer id) {
        CmsComment comment = findById(id);
        comment.setDowns((short) (comment.getDowns() + 1));
    }

    @Autowired
    private CmsSensitivityMng cmsSensitivityMng;
    @Autowired
    private CmsUserMng cmsUserMng;
    @Autowired
    private CmsSiteMng cmsSiteMng;
    @Autowired
    private ContentMng contentMng;
    @Autowired
    private ContentCountMng contentCountMng;
    @Autowired
    private CmsCommentExtMng cmsCommentExtMng;
    @Autowired
    private CmsCommentDao dao;
    @Autowired
    private CmsUserExtMng userExtMng;
}