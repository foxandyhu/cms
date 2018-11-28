package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.Date;
import java.util.List;

public interface ContentChargeDao {

    List<ContentCharge> getList(String contentTitle, Integer authorUserId,
                                Date buyTimeBegin, Date buyTimeEnd, int orderBy, Integer first, Integer count);

    Pagination getPage(String contentTitle, Integer authorUserId,
                       Date buyTimeBegin, Date buyTimeEnd,
                       int orderBy, int pageNo, int pageSize);

    ContentCharge findById(Integer id);

    ContentCharge save(ContentCharge bean);

    ContentCharge updateByUpdater(Updater<ContentCharge> updater);
}