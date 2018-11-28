package com.bfly.cms.vote.dao;

import com.bfly.cms.vote.entity.CmsVoteReply;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsVoteReplyDao {

    Pagination getPage(Integer subTopicId, int pageNo, int pageSize);

    CmsVoteReply findById(Integer id);

    CmsVoteReply save(CmsVoteReply bean);

    CmsVoteReply updateByUpdater(Updater<CmsVoteReply> updater);

    CmsVoteReply deleteById(Integer id);
}