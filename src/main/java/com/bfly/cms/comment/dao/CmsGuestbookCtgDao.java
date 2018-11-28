package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsGuestbookCtgDao {
    List<CmsGuestbookCtg> getList(Integer siteId);

    CmsGuestbookCtg findById(Integer id);

    CmsGuestbookCtg save(CmsGuestbookCtg bean);

    CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

    CmsGuestbookCtg deleteById(Integer id);
}