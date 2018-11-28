package com.bfly.cms.weixin.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.service.WeixinMessage;

import java.util.List;

public interface WeixinMessageMng {

    Pagination getPage(Integer siteId, int pageNo, int pageSize);

    List<WeixinMessage> getList(Integer siteId);

    WeixinMessage getWelcome(Integer siteId);

    WeixinMessage findByNumber(String number, Integer siteId);

    WeixinMessage findById(Integer id);

    WeixinMessage save(WeixinMessage bean);

    WeixinMessage update(WeixinMessage bean);

    WeixinMessage deleteById(Integer id);

    WeixinMessage[] deleteByIds(Integer[] ids);

}
