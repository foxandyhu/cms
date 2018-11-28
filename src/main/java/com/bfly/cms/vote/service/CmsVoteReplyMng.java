package com.bfly.cms.vote.service;

import com.bfly.cms.vote.entity.CmsVoteReply;
import com.bfly.common.page.Pagination;

public interface CmsVoteReplyMng {

    Pagination getPage(Integer subTopicId, int pageNo, int pageSize);

    CmsVoteReply findById(Integer id);

    CmsVoteReply save(CmsVoteReply bean);

    CmsVoteReply update(CmsVoteReply bean);

    CmsVoteReply deleteById(Integer id);

    CmsVoteReply[] deleteByIds(Integer[] ids);
}