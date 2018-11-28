package com.bfly.cms.statistic.dao;

import java.util.Date;

import com.bfly.cms.statistic.entity.CmsSiteAccessPages;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 * @author Tom
 */
public interface CmsSiteAccessPagesDao {

    CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex);

    Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize);

    CmsSiteAccessPages save(CmsSiteAccessPages access);

    CmsSiteAccessPages updateByUpdater(Updater<CmsSiteAccessPages> updater);

    void clearByDate(Date date);

}
