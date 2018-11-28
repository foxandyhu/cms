package com.bfly.cms.acquisition.service;

import java.util.Date;
import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisition;
import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;
import com.bfly.cms.acquisition.entity.CmsAcquisition.AcquisitionResultType;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ChannelDeleteChecker;

public interface CmsAcquisitionMng extends ChannelDeleteChecker {
	 List<CmsAcquisition> getList(Integer siteId);

	 CmsAcquisition findById(Integer id);

	 void stop(Integer id);

	 void pause(Integer id);

	 CmsAcquisition start(Integer id);

	 void end(Integer id);

	 boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem);

	 CmsAcquisition save(CmsAcquisition bean, Integer channelId,
			Integer typeId, Integer userId, Integer siteId,String[] keyword,String[] replaceWords
			,String[] shieldStarts,String[] shieldEnds);

	 CmsAcquisition update(CmsAcquisition bean, Integer channelId,
			Integer typeId,String[] keywords,String[] replaceWords
			,String[] shieldStarts,String[] shieldEnds);

	 CmsAcquisition deleteById(Integer id);

	 CmsAcquisition[] deleteByIds(Integer[] ids);

	 Content saveContent(String title, String txt, String origin,
						 String author, String description, Date releaseDate, Integer acquId,
						 AcquisitionResultType resultType, CmsAcquisitionTemp temp,
						 CmsAcquisitionHistory history, String typeImg);

	 CmsAcquisition getStarted(Integer siteId);
	
	 Integer getMaxQueue(Integer siteId);

	 Integer hasStarted(Integer siteId);
	
	 void addToQueue(Integer[] ids, Integer queueNum);
	
	 void cancel(Integer siteId, Integer id);
	
	 List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum);
	
	 CmsAcquisition popAcquFromQueue(Integer siteId);
}