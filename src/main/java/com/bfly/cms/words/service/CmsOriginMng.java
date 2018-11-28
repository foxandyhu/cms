package com.bfly.cms.words.service;

import com.bfly.cms.words.entity.CmsOrigin;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsOriginMng {
    Pagination getPage(int pageNo, int pageSize);

    List<CmsOrigin> getList(String name);

    CmsOrigin findById(Integer id);

    CmsOrigin save(CmsOrigin bean);

    CmsOrigin update(CmsOrigin bean);

    CmsOrigin deleteById(Integer id);

    CmsOrigin[] deleteByIds(Integer[] ids);

}