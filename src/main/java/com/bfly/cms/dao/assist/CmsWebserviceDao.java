package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsWebservice;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsWebserviceDao {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsWebservice> getList(String type);

    CmsWebservice findById(Integer id);

    CmsWebservice save(CmsWebservice bean);

    CmsWebservice updateByUpdater(Updater<CmsWebservice> updater);

    CmsWebservice deleteById(Integer id);
}