package com.bfly.cms.friendlink.service.impl;

import com.bfly.cms.friendlink.dao.IFriendLinkTypeDao;
import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.cms.friendlink.service.IFriendLinkTypeService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class FriendLinkTypeServiceImpl extends BaseServiceImpl<FriendLinkType, Integer> implements IFriendLinkTypeService {

    @Autowired
    private IFriendLinkTypeDao typeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortFriendLinkType(int upId, int downId) {
        FriendLinkType upItem = get(upId);
        Assert.notNull(upItem, "不存在的友情链接类型信息!");

        FriendLinkType downItem = get(downId);
        Assert.notNull(downItem, "不存在的友情链接类型信息!");

        typeDao.editFriendLinkTypeSeq(upId, downItem.getSeq());
        typeDao.editFriendLinkTypeSeq(downId, upItem.getSeq());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FriendLinkType friendLinkType) {
        int maxSeq = typeDao.getMaxSeq();
        friendLinkType.setSeq(++maxSeq);
        return super.save(friendLinkType);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FriendLinkType friendLinkType) {
        FriendLinkType type = get(friendLinkType.getId());
        Assert.notNull(type, "不存在的友情链接类型!");
        friendLinkType.setSeq(type.getSeq());
        return super.edit(friendLinkType);
    }
}
