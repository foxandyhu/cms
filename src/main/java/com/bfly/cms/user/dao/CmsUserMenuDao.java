package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsUserMenu;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsUserMenuDao {
    Pagination getPage(Integer userId, int pageNo, int pageSize);

    List<CmsUserMenu> getList(Integer userId, int count);

    CmsUserMenu findById(Integer id);

    CmsUserMenu save(CmsUserMenu bean);

    CmsUserMenu updateByUpdater(Updater<CmsUserMenu> updater);

    CmsUserMenu deleteById(Integer id);
}