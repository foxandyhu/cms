package com.bfly.cms.content.service.impl;

import java.util.Calendar;
import java.util.Date;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.content.dao.ContentCountDao;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCount;
import com.bfly.cms.content.service.ContentCountMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.service.CmsConfigMng;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentCountMngImpl implements ContentCountMng {
	
	@Override
    public int contentUp(Integer id) {
		ContentCount c = dao.findById(id);
		if (c == null) {
			return 0;
		}
		int count = c.getUps() + 1;
		c.setUps(count);
		c.setUpsMonth(c.getUpsMonth() + 1);
		c.setUpsWeek((short) (c.getUpsWeek() + 1));
		c.setUpsDay((short) (c.getUpsDay() + 1));
		return count;
	}

	@Override
    public int contentDown(Integer id) {
		ContentCount c = dao.findById(id);
		if (c == null) {
			return 0;
		}
		int count = c.getDowns() + 1;
		c.setDowns(count);
		return count;
	}

	@Override
    public void downloadCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setDownloads(c.getDownloads() + 1);
		c.setDownloadsMonth(c.getDownloadsMonth() + 1);
		c.setDownloadsWeek((short) (c.getCommentsWeek() + 1));
		c.setDownloadsDay((short) (c.getDownloadsDay() + 1));
	}

	@Override
    public void commentCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setComments(c.getComments() + 1);
		c.setCommentsMonth(c.getCommentsMonth() + 1);
		c.setCommentsWeek((short) (c.getCommentsWeek() + 1));
		c.setCommentsDay((short) (c.getCommentsDay() + 1));
	}

	@Override
    public int freshCacheToDB(Ehcache cache) {
		CmsConfig config = cmsConfigMng.get();
		clearCount(config);
		int count = dao.freshCacheToDB(cache);
		copyCount(config);
		return count;
	}

	private int clearCount(CmsConfig config) {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(config.getCountClearTime());
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			cmsConfigMng.updateCountClearTime(curr.getTime());
			return dao.clearCount(currWeek != lastWeek, currMonth != lastMonth);
		} else {
			return 0;
		}
	}

	private int copyCount(CmsConfig config) {
		long curr = System.currentTimeMillis();
		long last = config.getCountCopyTime().getTime();
		if (curr > interval + last) {
			cmsConfigMng.updateCountCopyTime(new Date(curr));
			return dao.copyCount();
		} else {
			return 0;
		}
	}

	@Override
    @Transactional(readOnly = true)
	public ContentCount findById(Integer id) {
		ContentCount entity = dao.findById(id);
		return entity;
	}

	@Override
    public ContentCount save(ContentCount count, Content content) {
		count.setContent(content);
		count.init();
		dao.save(count);
		content.setContentCount(count);
		return count;
	}

	@Override
    public ContentCount update(ContentCount bean) {
		Updater<ContentCount> updater = new Updater<ContentCount>(bean);
		ContentCount entity = dao.updateByUpdater(updater);
		return entity;
	}

	// 间隔时间
	private int interval = 60 * 60 * 1000; // 一小时
	@Autowired
	private CmsConfigMng cmsConfigMng;
	@Autowired
	private ContentCountDao dao;

	/**
	 * 设置拷贝间隔时间。默认一小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}


}