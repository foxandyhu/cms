package com.bfly.cms.acquisition.dao;

import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 9:56
 */
public interface CmsAcquisitionHistoryDao {

    List<CmsAcquisitionHistory> getList(Integer acquId);

    Pagination getPage(Integer acquId, Integer pageNo, Integer pageSize);

    CmsAcquisitionHistory findById(Integer id);

    CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

    CmsAcquisitionHistory updateByUpdater(Updater<CmsAcquisitionHistory> updater);

    CmsAcquisitionHistory deleteById(Integer id);

    void deleteByAcquisition(Integer acquId);

    Boolean checkExistByProperties(Boolean title, String value);
}