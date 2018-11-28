package com.bfly.cms.funds.dao;

import com.bfly.cms.funds.entity.CmsAccountPay;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;

public interface CmsAccountPayDao {
    Pagination getPage(String drawNum, Integer payUserId, Integer drawUserId,
                       Date payTimeBegin, Date payTimeEnd, int pageNo, int pageSize);

    CmsAccountPay findById(Long id);

    CmsAccountPay save(CmsAccountPay bean);

    CmsAccountPay updateByUpdater(Updater<CmsAccountPay> updater);

    CmsAccountPay deleteById(Long id);
}