package com.bfly.cms.vote.service;

import java.util.Date;

import com.bfly.cms.vote.entity.CmsVoteRecord;
import com.bfly.cms.vote.entity.CmsVoteTopic;
import com.bfly.cms.user.entity.CmsUser;

public interface CmsVoteRecordMng {
	 CmsVoteRecord save(CmsVoteTopic topic, CmsUser user, String ip,
			String cookie);

	 Date lastVoteTimeByUserId(Integer userId, Integer topicId);

	 Date lastVoteTimeByIp(String ip, Integer topicId);

	 Date lastVoteTimeByCookie(String cookie, Integer topicId);
}