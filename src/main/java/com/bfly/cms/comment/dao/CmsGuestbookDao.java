package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:45
 */
public interface CmsGuestbookDao {
    Pagination getPage(Integer ctgId, Integer ctgIds[], Integer userId, Boolean recommend, Short checked, boolean desc, boolean cacheable, int pageNo, int pageSize);

    List<CmsGuestbook> getList(Integer ctgId, Integer userId, Boolean recommend, Short checked, boolean desc, boolean cacheable, int first, int max);

    CmsGuestbook findById(Integer id);

    CmsGuestbook save(CmsGuestbook bean);

    CmsGuestbook updateByUpdater(Updater<CmsGuestbook> updater);

    CmsGuestbook deleteById(Integer id);
}