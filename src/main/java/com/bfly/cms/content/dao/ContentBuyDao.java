package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentBuy;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ContentBuyDao {
    Pagination getPage(String orderNum, Integer buyUserId, Integer authorUserId,
                       Short payMode, int pageNo, int pageSize);

    List<ContentBuy> getList(String orderNum, Integer buyUserId,
                             Integer authorUserId, Short payMode, Integer first, Integer count);

    Pagination getPageByContent(Integer contentId,
                                Short payMode, int pageNo, int pageSize);

    List<ContentBuy> getListByContent(Integer contentId,
                                      Short payMode, Integer first, Integer count);

    ContentBuy findById(Long id);

    ContentBuy findByOrderNumber(String orderNumber);

    ContentBuy findByOutOrderNum(String orderNum, Integer payMethod);

    ContentBuy find(Integer buyUserId, Integer contentId);

    ContentBuy save(ContentBuy bean);

    ContentBuy updateByUpdater(Updater<ContentBuy> updater);

    ContentBuy deleteById(Long id);
}