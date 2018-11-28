package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.Authentication;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;

public interface AuthenticationDao {

    int deleteExpire(Date d);

    Pagination getPage(int pageNo, int pageSize);

    Authentication findById(String id);

    Authentication save(Authentication bean);

    Authentication updateByUpdater(Updater<Authentication> updater);

    Authentication deleteById(String id);
}