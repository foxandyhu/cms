package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ApiAccount;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ApiAccountDao {
    Pagination getPage(int pageNo, int pageSize);

    List<ApiAccount> getList(int first, int count);

    ApiAccount findByAppId(String appId);

    ApiAccount findAdmin();

    ApiAccount findById(Integer id);

    ApiAccount save(ApiAccount bean);

    ApiAccount updateByUpdater(Updater<ApiAccount> updater);

    ApiAccount deleteById(Integer id);
}