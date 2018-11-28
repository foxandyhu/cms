package com.bfly.cms.acquisition.dao;

import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsAcquisitionHistoryDao {
    List<CmsAcquisitionHistory> getList(Integer siteId, Integer acquId);

    Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize);

    CmsAcquisitionHistory findById(Integer id);

    CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

    CmsAcquisitionHistory updateByUpdater(Updater<CmsAcquisitionHistory> updater);

    CmsAcquisitionHistory deleteById(Integer id);

    void deleteByAcquisition(Integer acquId);

    Boolean checkExistByProperties(Boolean title, String value);
}