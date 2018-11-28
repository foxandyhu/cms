package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.CmsGuestbookExt;
import com.bfly.common.hibernate4.Updater;

public interface CmsGuestbookExtDao {
    CmsGuestbookExt findById(Integer id);

    CmsGuestbookExt save(CmsGuestbookExt bean);

    CmsGuestbookExt updateByUpdater(Updater<CmsGuestbookExt> updater);

    CmsGuestbookExt deleteById(Integer id);
}