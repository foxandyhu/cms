package com.bfly.cms.weixin.dao;

import com.bfly.cms.weixin.entity.Weixin;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface WeixinDao {

    Pagination getPage(Integer siteId, int pageNo, int pageSize);

    Weixin save(Weixin bean);

    Weixin deleteById(Integer id);

    Weixin findById(Integer id);

    Weixin find(Integer siteId);

    Weixin updateByUpdater(Updater<Weixin> updater);
}
