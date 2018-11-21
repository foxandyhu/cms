package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsPlug;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsPlugDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsPlug> getList(String author, Boolean used);

    CmsPlug findById(Integer id);

    CmsPlug findByPath(String plugPath);

    CmsPlug save(CmsPlug bean);

    CmsPlug updateByUpdater(Updater<CmsPlug> updater);

    CmsPlug deleteById(Integer id);
}