package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ApiUserLogin;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

public interface ApiUserLoginDao {
    Pagination getPage(int pageNo, int pageSize);

    void clearByDate(Date end);

    List<ApiUserLogin> getList(Date end, int first, int count);

    ApiUserLogin findById(Long id);

    ApiUserLogin findUserLogin(String username, String sessionKey);

    ApiUserLogin save(ApiUserLogin bean);

    ApiUserLogin updateByUpdater(Updater<ApiUserLogin> updater);

    ApiUserLogin deleteById(Long id);
}