package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.CmsVoteItem;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsVoteItemDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsVoteItem findById(Integer id);

    CmsVoteItem save(CmsVoteItem bean);

    CmsVoteItem updateByUpdater(Updater<CmsVoteItem> updater);

    CmsVoteItem deleteById(Integer id);
}