package com.bfly.cms.friendlink.service.impl;

import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.cms.friendlink.service.IFriendLinkTypeService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FriendLinkTypeImpl extends BaseServiceImpl<FriendLinkType, Integer> implements IFriendLinkTypeService {
}