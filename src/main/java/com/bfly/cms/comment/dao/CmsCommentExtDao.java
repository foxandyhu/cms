package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsCommentExtDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsCommentExt findById(Integer id);

    CmsCommentExt save(CmsCommentExt bean);

    CmsCommentExt updateByUpdater(Updater<CmsCommentExt> updater);

    int deleteByContentId(Integer contentId);

    CmsCommentExt deleteById(Integer id);
}