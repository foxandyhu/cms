package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IChannelDao;
import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.IModelService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ChannelServiceImpl extends BaseServiceImpl<Channel, Integer> implements IChannelService {

    @Autowired
    private IChannelDao channelDao;
    @Autowired
    private IModelService modelService;

    @Override
    @Cacheable(value = "beanCache", key = "'channel_by_map_'+#exactQueryProperty.hashCode()")
    public List<Channel> getList(Map<String, Object> exactQueryProperty) {
        return Collections.synchronizedList(super.getList(exactQueryProperty));
    }

    @Override
    @Cacheable(value = "beanCache", key = "'channel_list'")
    public List<Map<String, Object>> getAllChannel() {
        List<Map<String, Object>> list = channelDao.getAllChannel();
        List<Map<String, Object>> array = new ArrayList<>();
        if (list != null) {
            list.forEach(map -> {
                Map<String, Object> hashMap = new HashMap<>(map.size());
                hashMap.putAll(map);
                array.add(hashMap);
            });
        }
        //  此处返回一个线程安全的集合 由于list可能会被写入缓存 造成读写冲突
        return Collections.synchronizedList(array);
    }

    @Override
    @CacheEvict(value = "beanCache", key = "'channel_list'")
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Channel channel) {
        Model model = modelService.get(channel.getModelId());
        Assert.notNull(model, "不存在的模型!");

        Channel channelDb = channelDao.getChannelByPath(channel.getPath());
        Assert.isTrue(channelDb == null, "栏目路径已存在!");

        int maxSeq = channelDao.getMaxSeq();
        channel.setSeq(++maxSeq);
        return super.save(channel);
    }

    @Override
    @CacheEvict(value = "beanCache", key = "'channel_list'")
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Channel channel) {
        Channel cl = get(channel.getId());
        Assert.notNull(cl, "不存在的栏目信息!");

        //修改了栏目路径
        if (!cl.getPath().equals(channel.getPath())) {
            Channel clDb = channelDao.getChannelByPath(channel.getPath());
            Assert.isTrue(clDb == null, "栏目路径已存在!");
        }

        channel.setModelId(cl.getModelId());
        channel.setSeq(cl.getSeq());
        return super.edit(channel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'channel_list'")
    public void sortChannel(int upChannelId, int downChannelId) {
        Channel upChannel = get(upChannelId);
        Assert.notNull(upChannel, "不存在的栏目信息!");

        Channel downChannel = get(downChannelId);
        Assert.notNull(downChannel, "不存在的栏目信息!");

        channelDao.editChannelSeq(upChannelId, downChannel.getSeq());
        channelDao.editChannelSeq(downChannelId, upChannel.getSeq());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'channel_list'")
    public int remove(Integer... ids) {
        for (Integer id : ids) {
            if (id != null) {
                channelDao.editChannelParent(id);
            }
        }
        return super.remove(ids);
    }

    @Override
    @Cacheable(value = "beanCache", key = "'channel_by_'+#path")
    public Channel getChannelByPath(String path) {
        return channelDao.getChannelByPath(path);
    }

    @Override
    @Cacheable(value = "beanCache", key = "'channel_by_parent_'+#parentId")
    public List<Channel> getChildren(int parentId) {
        return channelDao.getChildren(parentId);
    }
}
