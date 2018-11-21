package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsWebserviceCallRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsWebserviceCallRecordDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsWebserviceCallRecord findById(Integer id);

    CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean);

    CmsWebserviceCallRecord updateByUpdater(Updater<CmsWebserviceCallRecord> updater);

    CmsWebserviceCallRecord deleteById(Integer id);
}