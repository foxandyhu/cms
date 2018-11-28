package com.bfly.cms.content.service.impl;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.cms.content.dao.CmsTopicDao;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/27 10:30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsTopicMngImpl implements CmsTopicMng {

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CmsTopic> getListForTag(Integer channelId, boolean recommend,
                                        Integer first, Integer count) {
        return dao.getList(channelId, recommend, first, count, true);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Pagination getPageForTag(Integer channelId, boolean recommend,
                                    int pageNo, int pageSize) {
        return dao.getPage(channelId, null, recommend, pageNo, pageSize, true);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Pagination getPage(String initials, int pageNo, int pageSize) {
        Pagination page = dao.getPage(null, initials, false, pageNo, pageSize, false);
        return page;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<CmsTopic> getListByChannel(Integer channelId) {
        List<CmsTopic> list = dao.getGlobalTopicList();
        Channel c = channelMng.findById(channelId);
        list.addAll(dao.getListByChannelIds(c.getNodeIds()));
        return list;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public CmsTopic findById(Integer id) {
        CmsTopic entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsTopic save(CmsTopic bean, Integer channelId, Integer[] channelIds) {
        if (channelId != null) {
            bean.setChannel(channelMng.findById(channelId));
        }
        bean.init();
        bean = dao.save(bean);
        if (channelIds != null && channelIds.length > 0) {
            for (Integer cid : channelIds) {
                bean.addToChannels(channelMng.findById(cid));
            }
        }
        return bean;
    }

    @Override
    public CmsTopic update(CmsTopic bean, Integer channelId, Integer[] channelIds) {
        Updater<CmsTopic> updater = new Updater<CmsTopic>(bean);
        CmsTopic entity = dao.updateByUpdater(updater);
        if (channelId != null) {
            entity.setChannel(channelMng.findById(channelId));
        } else {
            entity.setChannel(null);
        }
        entity.blankToNull();
        Set<Channel> channels = entity.getChannels();
        channels.clear();
        if (channelIds != null && channelIds.length > 0) {
            for (Integer cid : channelIds) {
                channels.add(channelMng.findById(cid));
            }
        }
        return entity;
    }

    @Override
    public CmsTopic deleteById(Integer id) {
        dao.deleteContentRef(id);
        CmsTopic bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsTopic[] deleteByIds(Integer[] ids) {
        CmsTopic[] beans = new CmsTopic[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public CmsTopic[] updatePriority(Integer[] ids, Integer[] priority) {
        int len = ids.length;
        CmsTopic[] beans = new CmsTopic[len];
        for (int i = 0; i < len; i++) {
            beans[i] = findById(ids[i]);
            beans[i].setPriority(priority[i]);
        }
        return beans;
    }

    @Override
    public String checkForChannelDelete(Integer channelId) {
        if (dao.countByChannelId(channelId) > 0) {
            return "cmsTopic.error.cannotDeleteChannel";
        } else {
            return null;
        }
    }

    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsTopicDao dao;
}