package com.bfly.cms.friendlink.service.impl;

import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.cms.friendlink.service.IFriendLinkService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class FriendLinkImpl extends BaseServiceImpl<FriendLink, Integer> implements IFriendLinkService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FriendLink friendLink) {
        String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(friendLink.getLogo(), ResourceConfig.getFriendLinkDir());
        friendLink.setLogo(img);
        return super.save(friendLink);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FriendLink friendLink) {
        String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(friendLink.getLogo(), ResourceConfig.getFriendLinkDir());
        friendLink.setLogo(img);
        return super.edit(friendLink);
    }
}
