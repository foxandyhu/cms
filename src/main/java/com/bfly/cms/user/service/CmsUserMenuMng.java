package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.CmsUserMenu;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsUserMenuMng {
    Pagination getPage(Integer userId, int pageNo, int pageSize);

    List<CmsUserMenu> getList(Integer userId, int cout);

    CmsUserMenu findById(Integer id);

    CmsUserMenu save(CmsUserMenu bean);

    CmsUserMenu update(CmsUserMenu bean);

    CmsUserMenu deleteById(Integer id);

    CmsUserMenu[] deleteByIds(Integer[] ids);
}