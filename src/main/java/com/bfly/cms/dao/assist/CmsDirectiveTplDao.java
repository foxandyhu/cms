package com.bfly.cms.dao.assist;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.entity.assist.CmsDirectiveTpl;

public interface CmsDirectiveTplDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsDirectiveTpl> getList(int count);

    CmsDirectiveTpl findById(Integer id);

    CmsDirectiveTpl save(CmsDirectiveTpl bean);

    CmsDirectiveTpl updateByUpdater(Updater<CmsDirectiveTpl> updater);

    CmsDirectiveTpl deleteById(Integer id);
}