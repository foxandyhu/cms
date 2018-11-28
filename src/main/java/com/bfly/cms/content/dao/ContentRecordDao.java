package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ContentRecordDao {
    Pagination getPage(int pageNo, int pageSize);

    ContentRecord findById(Long id);

    ContentRecord save(ContentRecord bean);

    ContentRecord updateByUpdater(Updater<ContentRecord> updater);

    ContentRecord deleteById(Long id);

    List<ContentRecord> getListByContentId(Integer contentId);
}