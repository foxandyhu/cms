package com.bfly.cms.friendlink.service;

import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.core.base.service.IBaseService;

/**
 * 友情链接类型业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:55
 */
public interface IFriendLinkTypeService extends IBaseService<FriendLinkType, Integer> {

    /**
     * 友情链接类型排序
     *
     * @param downId 下移ID
     * @param upId   上移ID
     * @author andy_hulibo@163.com
     * @date 2019/8/6 18:29
     */
    void sortFriendLinkType(int upId, int downId);
}
