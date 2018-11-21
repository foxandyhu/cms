package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ApiRecord;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface ApiRecordDao {
    Pagination getPage(int pageNo, int pageSize);

    ApiRecord findBySign(String sign, String appId);

    ApiRecord findById(Long id);

    ApiRecord save(ApiRecord bean);

    ApiRecord updateByUpdater(Updater<ApiRecord> updater);

    ApiRecord deleteById(Long id);
}