package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsAccountDraw;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

public interface CmsAccountDrawDao {
    Pagination getPage(Integer userId, Short applyStatus,
                       Date applyTimeBegin, Date applyTimeEnd, int pageNo, int pageSize);

    List<CmsAccountDraw> getList(Integer userId, Short applyStatus,
                                 Date applyTimeBegin, Date applyTimeEnd, Integer first, Integer count);

    List<CmsAccountDraw> getList(Integer userId, Short[] status, Integer count);

    CmsAccountDraw findById(Integer id);

    CmsAccountDraw save(CmsAccountDraw bean);

    CmsAccountDraw updateByUpdater(Updater<CmsAccountDraw> updater);

    CmsAccountDraw deleteById(Integer id);
}