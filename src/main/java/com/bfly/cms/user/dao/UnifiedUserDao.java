package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/2 17:16
 */
public interface UnifiedUserDao {

    UnifiedUser getByUsername(String username);

    int countByEmail(String email);

    Pagination getPage(int pageNo, int pageSize);

    UnifiedUser findById(Integer id);

    UnifiedUser save(UnifiedUser bean);

    UnifiedUser updateByUpdater(Updater<UnifiedUser> updater);

    UnifiedUser deleteById(Integer id);
}