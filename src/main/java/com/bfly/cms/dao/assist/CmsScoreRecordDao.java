package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsScoreRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsScoreRecordDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsScoreRecord> findListByContent(Integer contentId);

    CmsScoreRecord findByScoreItemContent(Integer itemId, Integer contentId);

    CmsScoreRecord findById(Integer id);

    CmsScoreRecord save(CmsScoreRecord bean);

    CmsScoreRecord updateByUpdater(Updater<CmsScoreRecord> updater);

    CmsScoreRecord deleteById(Integer id);
}