package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IChannelDao;
import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.Model;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.IModelService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

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
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Channel channel) {
        Model model = modelService.get(channel.getModelId());
        Assert.notNull(model, "不存在的模型!");

        int maxSeq = channelDao.getMaxSeq();
        channel.setSeq(++maxSeq);
        return super.save(channel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Channel channel) {
        Channel cl = get(channel.getId());
        Assert.notNull(cl, "不存在的栏目信息!");

        channel.setModelId(cl.getModelId());
        channel.setSeq(cl.getSeq());
        return super.edit(channel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    public int remove(Integer... ids) {
        for (Integer id : ids) {
            if (id != null) {
                channelDao.editChannelParent(id);
            }
        }
        return super.remove(ids);
    }
}
