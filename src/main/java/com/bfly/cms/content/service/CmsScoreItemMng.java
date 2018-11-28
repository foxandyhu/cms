package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.CmsScoreItem;
import com.bfly.common.page.Pagination;

public interface CmsScoreItemMng {
    Pagination getPage(Integer groupId, int pageNo, int pageSize);

    CmsScoreItem findById(Integer id);

    CmsScoreItem save(CmsScoreItem bean);

    CmsScoreItem update(CmsScoreItem bean);

    CmsScoreItem deleteById(Integer id);

    CmsScoreItem[] deleteByIds(Integer[] ids);
}