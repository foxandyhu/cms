package com.bfly.cms.friendlink.service;

import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.core.base.service.IBaseService;

/**
 * 友情链接业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:55
 */
public interface IFriendLinkService extends IBaseService<FriendLink, Integer> {

    /**
     * 友情链接排序
     *
     * @param downId 下移ID
     * @param upId   上移ID
     * @author andy_hulibo@163.com
     * @date 2019/8/6 18:29
     */
    void sortFriendLink(int upId, int downId);
}
