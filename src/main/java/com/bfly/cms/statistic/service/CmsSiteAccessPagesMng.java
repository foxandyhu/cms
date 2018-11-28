package com.bfly.cms.statistic.service;

import com.bfly.cms.statistic.entity.CmsSiteAccessPages;
import com.bfly.common.page.Pagination;

import java.util.Date;

/**
 * @author Tom
 */
public interface CmsSiteAccessPagesMng {

    CmsSiteAccessPages save(CmsSiteAccessPages access);

    CmsSiteAccessPages update(CmsSiteAccessPages access);

    CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex);

    Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize);

    void clearByDate(Date date);
}
