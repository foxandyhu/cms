package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ApiInfo;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface ApiInfoDao {
    Pagination getPage(int pageNo, int pageSize);

    ApiInfo findByUrl(String url);

    ApiInfo findById(Integer id);

    ApiInfo save(ApiInfo bean);

    ApiInfo updateByUpdater(Updater<ApiInfo> updater);

    ApiInfo deleteById(Integer id);
}