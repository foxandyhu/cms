package com.bfly.cms.acquisition.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.acquisition.dao.CmsAcquisitionDao;
import com.bfly.cms.acquisition.entity.CmsAcquisition;
import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.cms.acquisition.entity.CmsAcquisitionReplace;
import com.bfly.cms.acquisition.entity.CmsAcquisitionShield;
import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;
import com.bfly.cms.acquisition.entity.CmsAcquisition.AcquisitionResultType;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.cms.content.entity.ContentExt;
import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.cms.acquisition.service.CmsAcquisitionHistoryMng;
import com.bfly.cms.acquisition.service.CmsAcquisitionMng;
import com.bfly.cms.acquisition.service.CmsAcquisitionReplaceMng;
import com.bfly.cms.acquisition.service.CmsAcquisitionShieldMng;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.content.service.ContentTypeMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.service.CmsUserMng;

@Service
@Transactional
public class CmsAcquisitionMngImpl implements CmsAcquisitionMng{
	@Override
    @Transactional(readOnly = true)
	public List<CmsAcquisition> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Override
    @Transactional
	public CmsAcquisition findById(Integer id) {
		CmsAcquisition entity = dao.findById(id);
		return entity;
	}

	@Override
    public void stop(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsAcquisition.START) {
			acqu.setStatus(CmsAcquisition.STOP);
		} else if (acqu.getStatus() == CmsAcquisition.PAUSE) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
		}
	}

	@Override
    public void pause(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsAcquisition.START) {
			acqu.setStatus(CmsAcquisition.PAUSE);
		}
	}

	@Override
    public CmsAcquisition start(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return acqu;
		}
		acqu.setStatus(CmsAcquisition.START);
		acqu.setStartTime(new Date());
		acqu.setEndTime(null);
		if (acqu.getCurrNum() <= 0) {
			acqu.setCurrNum(1);
		}
		if (acqu.getCurrItem() <= 0) {
			acqu.setCurrItem(1);
		}
		acqu.setTotalItem(0);
		return acqu;
	}

	@Override
    public void end(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		acqu.setStatus(CmsAcquisition.STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(0);
		acqu.setCurrItem(0);
		acqu.setTotalItem(0);
		acqu.setTotalItem(0);
	}

	@Override
    public boolean isNeedBreak(Integer id, int currNum, int currItem,
                               int totalItem) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return true;
		} else if (acqu.isPuase()) {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			acqu.setEndTime(new Date());
			return true;
		} else if (acqu.isStop()) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
			acqu.setEndTime(new Date());
			return true;
		} else {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			return false;
		}
	}

	@Override
    public CmsAcquisition save(CmsAcquisition bean, Integer channelId,
                               Integer typeId, Integer userId, Integer siteId, String[] keyword, String[] replaceWords
			, String[] shieldStarts, String[] shieldEnds) {
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		bean.setUser(cmsUserMng.findById(userId));
		bean.setSite(cmsSiteMng.findById(siteId));
		bean.init();
		dao.save(bean);
		if (keyword!=null&&keyword.length>0) {
			saveReplace(bean,keyword,replaceWords);
		}
		if (shieldStarts!=null&&shieldStarts.length>0) {
			saveShield(bean,shieldStarts,shieldEnds);
		}
		return bean;
	}
	
	private void saveReplace(CmsAcquisition bean,String[] keyword,String[] replaceWords){
		for (int i = 0; i < keyword.length; i++) {
			CmsAcquisitionReplace replace=new CmsAcquisitionReplace();
			replace.setAcquisition(bean);
			replace.setKeyword(keyword[i]);
			replace.setReplaceWord(replaceWords[i]);
			cmsAcquisitionReplaceMng.save(replace);
		}
	}
	
	private void saveShield(CmsAcquisition bean,String[] shieldStarts,String[] shieldEnds){
		for (int i = 0; i < shieldStarts.length; i++) {
			CmsAcquisitionShield replace=new CmsAcquisitionShield();
			replace.setAcquisition(bean);
			replace.setShieldStart(shieldStarts[i]);
			replace.setShieldEnd(shieldEnds[i]);
			cmsAcquisitionShieldMng.save(replace);
		}
	}

	@Override
    public CmsAcquisition update(CmsAcquisition bean, Integer channelId,
                                 Integer typeId, String[] keyword, String[] replaceWords
			, String[] shieldStarts, String[] shieldEnds) {
		Updater<CmsAcquisition> updater = new Updater<CmsAcquisition>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		bean.getReplaceWords().clear();
		if (keyword!=null&&keyword.length>0) {
			saveReplace(bean,keyword,replaceWords);
		}	
		bean.getShields().clear();
		if (shieldStarts!=null&&shieldStarts.length>0) {
			saveShield(bean, shieldStarts, shieldEnds);
		}	
		return bean;
	}	

	@Override
    public CmsAcquisition deleteById(Integer id) {
		//删除采集记录
		acquisitionHistoryMng.deleteByAcquisition(id);
		CmsAcquisition bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsAcquisition[] deleteByIds(Integer[] ids) {
		CmsAcquisition[] beans = new CmsAcquisition[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public Content saveContent(String title, String txt, String origin,
                               String author, String description, Date releaseDate, Integer acquId,
                               AcquisitionResultType resultType, CmsAcquisitionTemp temp,
                               CmsAcquisitionHistory history, String typeImg) {
		CmsAcquisition acqu = findById(acquId);
		Content c = new Content();
		c.setSite(acqu.getSite());
		c.setModel(modelMng.getDefModel());
		c.setSortDate(releaseDate);
		ContentExt cext = new ContentExt();
		ContentTxt ctxt = new ContentTxt();
		cext.setAuthor(author);
		cext.setOrigin(origin);
		cext.setReleaseDate(releaseDate);
		cext.setTitle(title);
		cext.setDescription(description);
		ctxt.setTxt(txt);
		if (StringUtils.isNotBlank(typeImg)) {
			cext.setTypeImg(typeImg);
		}
		Content content = contentMng.save(c, cext, ctxt,null, null, null, null,
				null, null, null, null, null, acqu.getChannel().getId(), acqu.getType().getId(),
				false, false,ContentCharge.MODEL_FREE,
				0d,false,0d,0d,null,acqu.getUser(),false);
		history.setTitle(title);
		history.setContent(content);
		history.setDescription(resultType.name());
		temp.setTitle(title);
		temp.setDescription(resultType.name());
		return content;
	}

	@Override
    public String checkForChannelDelete(Integer channelId) {
		if (dao.countByChannelId(channelId) > 0) {
			return "cmsAcquisition.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}

	@Override
    public CmsAcquisition getStarted(Integer siteId) {
		return dao.getStarted(siteId);
	}

	@Override
    public Integer hasStarted(Integer siteId) {
		return getStarted(siteId) == null ? 0 : getMaxQueue(siteId) + 1;
	}

	@Override
    public Integer getMaxQueue(Integer siteId) {
		return dao.getMaxQueue(siteId);
	}

	@Override
    public void addToQueue(Integer[] ids, Integer queueNum) {
		for (Integer id : ids) {
			CmsAcquisition acqu = findById(id);
			if (acqu.getStatus() == CmsAcquisition.START || acqu.getQueue() > 0) {
				continue;
			}
			acqu.setQueue(queueNum++);
		}
	}

	@Override
    public void cancel(Integer siteId, Integer id) {
		CmsAcquisition acqu = findById(id);
		Integer queue = acqu.getQueue();
		for (CmsAcquisition c : getLargerQueues(siteId, queue)) {
			c.setQueue(c.getQueue() - 1);
		}
		acqu.setQueue(0);
	}

	@Override
    public List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum) {
		return dao.getLargerQueues(siteId, queueNum);
	}

	@Override
    public CmsAcquisition popAcquFromQueue(Integer siteId) {
		CmsAcquisition acquisition = dao.popAcquFromQueue(siteId);
		if (acquisition != null) {
			Integer id = acquisition.getId();
			cancel(siteId, id);
		}
		return acquisition;
	}

	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsAcquisitionDao dao;
	@Autowired
	private CmsModelMng modelMng;
	@Autowired
	private CmsAcquisitionHistoryMng acquisitionHistoryMng;
	@Autowired
	private CmsAcquisitionReplaceMng cmsAcquisitionReplaceMng;
	@Autowired
	private CmsAcquisitionShieldMng cmsAcquisitionShieldMng;
}