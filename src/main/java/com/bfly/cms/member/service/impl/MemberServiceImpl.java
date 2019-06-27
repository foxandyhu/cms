package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.security.Md5PwdEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:39
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberServiceImpl extends BaseServiceImpl<Member, Integer> implements IMemberService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member login(String username, String password) {
        Member member = null;
        boolean loginSuccess = false;
        try {
            Assert.hasText(username, "用户名不能为空!");
            Assert.hasText(password, "密码不能为空!");
            member = get(new HashMap<String, Object>(1) {{
                put("username", username);
            }});
            Assert.notNull(member, "用户名或密码不正确!");
            loginSuccess = true;
            boolean flag = new Md5PwdEncoder().isPasswordValid(member.getPassword(), password);
            Assert.isTrue(flag, "用户名或密码错误!");
            Assert.isTrue(Member.UNCHECK_STATUS != member.getStatus(), "此账号正在审核中!");
            Assert.isTrue(Member.DISABLE_STATUS != member.getStatus(), "此账号已被禁用!");
        } finally {
            updateLoginInfo(member, loginSuccess);
        }
        return member;
    }

    /**
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    protected void updateLoginInfo(Member member, boolean loginSuccess) {
        if (loginSuccess) {
            member.setLoginCount(member.getLoginCount() + 1);
            member.setLastLoginTime(new Date());
            member.setLastLoginIp(IpThreadLocal.get());
            member.setErrorTime(null);
            member.setErrorIp(null);
            member.setErrorCount(0);
        } else {
            member.setLastLoginTime(null);
            member.setLastLoginIp(null);
            member.setErrorTime(new Date());
            member.setErrorIp(IpThreadLocal.get());
            member.setErrorCount(member.getErrorCount() + 1);
        }
        super.edit(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Member member) {
        Assert.notNull(member, "会员信息不能为空!");

        member.setPassword(new Md5PwdEncoder().encodePassword(member.getPassword()));
        member.setActivity(false);
        return super.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Member member) {
        Assert.notNull(member, "会员信息为空!");
        Member orMember = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -6960710939610470029L;

            {
                put("username", member.getUsername());
            }
        });
        Assert.notNull(orMember, "不能存在该会员!");

        if (StringUtils.hasLength(member.getPassword())) {
            orMember.setPassword(new Md5PwdEncoder().encodePassword(member.getPassword()));
        }
        orMember.setStatus(member.getStatus());
        orMember.setGroup(member.getGroup());
        return super.edit(orMember);
    }
}
