package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsGuestbook;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsGuestbookDao {
    Pagination getPage(Integer siteId, Integer ctgId, Integer ctgIds[],
                       Integer userId, Boolean recommend, Short checked, boolean desc,
                       boolean cacheable, int pageNo, int pageSize);

    List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
                               Integer userId, Boolean recommend, Short checked, boolean desc,
                               boolean cacheable, int first, int max);

    CmsGuestbook findById(Integer id);

    CmsGuestbook save(CmsGuestbook bean);

    CmsGuestbook updateByUpdater(Updater<CmsGuestbook> updater);

    CmsGuestbook deleteById(Integer id);
}