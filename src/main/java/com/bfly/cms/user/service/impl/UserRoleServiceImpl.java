package com.bfly.cms.user.service.impl;

import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserRoleService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 15:48
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Integer> implements IUserRoleService {
}
