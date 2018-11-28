package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * 用户DAO接口
 */
public interface CmsUserDao {
    Pagination getPage(String username, String email, Integer siteId,
                       Integer groupId, Integer statu, Boolean admin, Integer rank,
                       String realName, Integer roleId, Boolean allChannel,
                       Boolean allControlChannel, int pageNo,
                       int pageSize);

    List<CmsUser> getList(String username, String email, Integer siteId,
                          Integer groupId, Integer statu, Boolean admin, Integer rank);

    List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
                               Integer statu, Integer rank);

    Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize);

    CmsUser findById(Integer id);

    CmsUser findByUsername(String username);

    int countByUsername(String username);

    int countMemberByUsername(String username);

    int countByEmail(String email);

    CmsUser save(CmsUser bean);

    CmsUser updateByUpdater(Updater<CmsUser> updater);

    CmsUser deleteById(Integer id);
}