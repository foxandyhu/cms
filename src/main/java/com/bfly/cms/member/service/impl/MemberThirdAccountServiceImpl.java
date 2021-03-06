package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.MemberThirdAccount;
import com.bfly.cms.member.service.IMemberThirdAccountService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:20
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberThirdAccountServiceImpl extends BaseServiceImpl<MemberThirdAccount, Integer> implements IMemberThirdAccountService {

}
