package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsOrigin;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsOriginDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsOrigin> getList(String name);

    CmsOrigin findById(Integer id);

    CmsOrigin save(CmsOrigin bean);

    CmsOrigin updateByUpdater(Updater<CmsOrigin> updater);

    CmsOrigin deleteById(Integer id);
}