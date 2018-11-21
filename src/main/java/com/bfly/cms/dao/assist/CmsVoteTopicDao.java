package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsVoteTopic;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsVoteTopicDao {
    Pagination getPage(Integer siteId, Short statu, int pageNo, int pageSize);

    List<CmsVoteTopic> getList(Boolean def, Integer siteId, Integer first, int count);

    CmsVoteTopic getDefTopic(Integer siteId);

    CmsVoteTopic findById(Integer id);

    CmsVoteTopic save(CmsVoteTopic bean);

    CmsVoteTopic updateByUpdater(Updater<CmsVoteTopic> updater);

    CmsVoteTopic deleteById(Integer id);
}