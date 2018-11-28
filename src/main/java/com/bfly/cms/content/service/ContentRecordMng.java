package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentRecord;
import com.bfly.cms.content.entity.ContentRecord.ContentOperateType;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ContentRecordMng {
    Pagination getPage(int pageNo, int pageSize);

    List<ContentRecord> getListByContentId(Integer contentId);

    ContentRecord record(Content content, CmsUser user, ContentOperateType operate);

    ContentRecord findById(Long id);

    ContentRecord save(ContentRecord bean);

    ContentRecord update(ContentRecord bean);

    ContentRecord deleteById(Long id);

    ContentRecord[] deleteByIds(Long[] ids);
}