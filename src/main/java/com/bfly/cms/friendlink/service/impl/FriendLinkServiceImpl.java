package com.bfly.cms.friendlink.service.impl;

import com.bfly.cms.friendlink.dao.IFriendLinkDao;
import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.cms.friendlink.service.IFriendLinkService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, Integer> implements IFriendLinkService {

    @Autowired
    private IFriendLinkDao friendLinkDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FriendLink friendLink) {
        String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(friendLink.getLogo(), ResourceConfig.getFriendLinkDir());
        friendLink.setLogo(img);
        int maxSeq = friendLinkDao.getMaxSeq();
        friendLink.setSeq(++maxSeq);
        return super.save(friendLink);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FriendLink friendLink) {
        FriendLink link = get(friendLink.getId());
        Assert.notNull(link, "不存在的链接信息!");

        String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(friendLink.getLogo(), ResourceConfig.getFriendLinkDir());
        if (!StringUtils.hasLength(img)) {
            img = link.getLogo();
        }
        friendLink.setLogo(img);
        friendLink.setSeq(link.getSeq());
        return super.edit(friendLink);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortFriendLink(int upId, int downId) {
        FriendLink upItem = get(upId);
        Assert.notNull(upItem, "不存在的友情链接信息!");

        FriendLink downItem = get(downId);
        Assert.notNull(downItem, "不存在的友情链接信息!");

        friendLinkDao.editFriendLinkSeq(upId, downItem.getSeq());
        friendLinkDao.editFriendLinkSeq(downId, upItem.getSeq());
    }

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Pageable pageable = getPageRequest(pager);

        Integer type = null;
        String typeStr = "type";
        if (property!=null && property.containsKey(typeStr)) {
            type = (Integer) property.get(typeStr);
        }
        Page<Map<String, Object>> page = friendLinkDao.getFriendLinkPage(type, pageable);
        pager.setTotalCount(page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }
}
