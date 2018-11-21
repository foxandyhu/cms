package com.bfly.cms.manager.assist.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsVoteTopicDao;
import com.bfly.cms.entity.assist.CmsVoteItem;
import com.bfly.cms.entity.assist.CmsVoteReply;
import com.bfly.cms.entity.assist.CmsVoteSubTopic;
import com.bfly.cms.entity.assist.CmsVoteTopic;
import com.bfly.cms.manager.assist.CmsVoteItemMng;
import com.bfly.cms.manager.assist.CmsVoteRecordMng;
import com.bfly.cms.manager.assist.CmsVoteReplyMng;
import com.bfly.cms.manager.assist.CmsVoteSubTopicMng;
import com.bfly.cms.manager.assist.CmsVoteTopicMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.entity.CmsUser;

@Service
@Transactional
public class CmsVoteTopicMngImpl implements CmsVoteTopicMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer siteId,Short statu, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId,statu, pageNo, pageSize);
		return page;
	}
	
	@Override
    @Transactional(readOnly = true)
	public  List<CmsVoteTopic> getList(Boolean def,Integer siteId,
			Integer first, int count){
		return dao.getList(def,siteId,first,count);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsVoteTopic findById(Integer id) {
		CmsVoteTopic entity = dao.findById(id);
		return entity;
	}

	@Override
    @Transactional(readOnly = true)
	public CmsVoteTopic getDefTopic(Integer siteId) {
		return dao.getDefTopic(siteId);
	}

	@Override
    public CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics) {
		int totalCount = 0;
		bean.setTotalCount(totalCount);
		bean.init();
		dao.save(bean);
		cmsVoteSubtopicMng.save(bean,subTopics);
		return bean;
	}
	
	@Override
    public CmsVoteTopic save(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics, Map<Integer,Set<CmsVoteItem>>items){
		int totalCount = 0;
		bean.setTotalCount(totalCount);
		bean.init();
		dao.save(bean);
		cmsVoteSubtopicMng.save(bean,subTopics);
		Iterator<CmsVoteSubTopic>iterator=subTopics.iterator();
		while(iterator.hasNext()){
			CmsVoteSubTopic subTopic=iterator.next();
			cmsVoteItemMng.save(items.get(subTopic.getVoteIndex()), subTopic);
		}
		return bean;
	}
	
	@Override
	public CmsVoteTopic save(CmsVoteTopic bean) {
		int totalCount = 0;
		bean.setTotalCount(totalCount);
		bean.init();
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsVoteTopic update(CmsVoteTopic bean) {
		Updater<CmsVoteTopic> updater = new Updater<CmsVoteTopic>(bean);
		updater.include(CmsVoteTopic.PROP_START_TIME);
		updater.include(CmsVoteTopic.PROP_END_TIME);
		updater.include(CmsVoteTopic.PROP_REPEATE_HOUR);
		bean = dao.updateByUpdater(updater);
		return bean;
	}
	
	@Override
    public CmsVoteTopic update(CmsVoteTopic bean, Set<CmsVoteSubTopic> subTopics, Map<Integer,Set<CmsVoteItem>>items){
		bean=update(bean);
		bean.getSubtopics().clear();
		bean.getItems().clear();
		cmsVoteSubtopicMng.save(bean,subTopics);
		Iterator<CmsVoteSubTopic>iterator=subTopics.iterator();
		while(iterator.hasNext()){
			CmsVoteSubTopic subTopic=iterator.next();
			cmsVoteItemMng.save(items.get(subTopic.getVoteIndex()), subTopic);
		}
		return bean;
	}
	
	@Override
	public void updatePriority(Integer[] ids, Integer defId, Boolean[] disableds,Integer siteId) {
		if (ids!=null && disableds!=null) {
			if (ids.length!=disableds.length) {
				return;
			}
		}
		CmsVoteTopic bean;
		for (int i = 0, len = ids.length; i < len; i++) {
			bean = findById(ids[i]);
			bean.setDisabled(disableds[i]);
		}
		if (defId!=null) {
			CmsVoteTopic topic = dao.getDefTopic(siteId);
			if (topic!=null&&!defId.equals(topic.getId())) {
				topic.setDef(false);
			}
			CmsVoteTopic defTopic = findById(defId);
			if (defTopic!=null) {
				defTopic.setDef(true);
			}
		}
	}

	@Override
    public CmsVoteTopic vote(Integer topicId, Integer[]subIds,
                             List<Integer[]> itemIds, String[]replys, CmsUser user,
                             String ip, String cookie) {
		CmsVoteTopic topic = findById(topicId);
		Set<CmsVoteItem> items = topic.getItems();
		List<Integer>itemIdsList=new ArrayList<Integer>();
		for(Integer[]itemId:itemIds){
			if(itemId!=null&&itemId.length>0){
				for(Integer id:itemId){
					itemIdsList.add(id);
				}
			}
		}
		Integer[]ids=new Integer[itemIdsList.size()];
		ids=(Integer[]) itemIdsList.toArray(ids);
		for (CmsVoteItem item : items) {
			if (ArrayUtils.contains(ids, item.getId())) {
				item.setVoteCount(item.getVoteCount() + 1);
			}
		}
		String reply;
		if(replys!=null&&replys.length>0){
			for(int i=0;i<replys.length;i++){
				reply=replys[i];
				if(StringUtils.isNotBlank(reply)){
					CmsVoteReply voteReply=new CmsVoteReply();
					voteReply.setReply(reply);
					voteReply.setSubTopic(cmsVoteSubtopicMng.findById(subIds[i]));
					cmsVoteReplyMng.save(voteReply);
				}
			}
		}
		//每个选项计一票改成每次提及整个调查计一票
		topic.setTotalCount(topic.getTotalCount()+1);
		// 如果需要限制投票，则需保存投票记录。
		if ((topic.getRepeateHour() == null || topic.getRepeateHour() > 0)
				&& (topic.getRestrictMember() || topic.getRestrictIp() || topic
						.getRestrictCookie())) {
			cmsVoteRecordMng.save(topic, user, ip, cookie);
		}
		return topic;
	}

	@Override
    public CmsVoteTopic deleteById(Integer id) {
		CmsVoteTopic bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsVoteTopic[] deleteByIds(Integer[] ids) {
		CmsVoteTopic[] beans = new CmsVoteTopic[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsVoteSubTopicMng cmsVoteSubtopicMng;
	@Autowired
	private CmsVoteReplyMng cmsVoteReplyMng;
	@Autowired
	private CmsVoteRecordMng cmsVoteRecordMng;
	@Autowired
	private CmsVoteTopicDao dao;
	@Autowired
	private CmsVoteItemMng cmsVoteItemMng;
}