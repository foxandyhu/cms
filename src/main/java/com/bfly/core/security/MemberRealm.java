package com.bfly.core.security;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.core.context.AutoLoginThreadLocal;
import com.bfly.core.enums.MemberStatus;
import com.bfly.core.exception.AccountUnActiveException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * Member Realm
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/16 17:18
 */
@Component("memberRealm")
public class MemberRealm extends AuthorizingRealm {

    @Autowired
    protected IMemberService memberService;
    @Autowired
    private IUserService userService;

    /**
     * 会员中心登录认证
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/1 11:46
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        LoginToken token = (LoginToken) authcToken;
        SimpleAuthenticationInfo info;
        if (token.isAdmin()) {
            info = userAuthentication(token.getUsername());
        } else {
            info = memberAuthentication(token.getUsername());
        }
        return info;
    }

    /**
     * 管理员验证
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/17 16:04
     */
    private SimpleAuthenticationInfo userAuthentication(String userName) {
        SimpleAuthenticationInfo info = null;
        User user = userService.getUser(userName);
        if (user != null) {
            Assert.isTrue(User.UNCHECK_STATUS != user.getStatus(), "此账号正在审核中!");
            Assert.isTrue(User.DISABLE_STATUS != user.getStatus(), "此账号已被禁用!");
            info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        return info;
    }

    /**
     * 会员验证
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/17 16:03
     */
    private SimpleAuthenticationInfo memberAuthentication(String userName) {
        Member member = memberService.getMember(userName);
        SimpleAuthenticationInfo info = null;
        if (member != null) {
            if (!member.isActivated()) {
                throw new AccountUnActiveException("此账号用户尚未激活!");
            }
            Assert.isTrue(MemberStatus.UNCHECK.getId() != member.getStatus(), "此账号正在审核中!");
            Assert.isTrue(MemberStatus.DISABLE.getId() != member.getStatus(), "此账号已被禁用!");
            Boolean isAutoLogin = AutoLoginThreadLocal.get();
            String password = member.getPassword();
            if (isAutoLogin != null && isAutoLogin) {
                // 自动登录 数据库密码再次进行加密然后和shiro存储的密码比对 因为shiro会对密码进行加密
                password = new Md5PwdEncoder().encodePassword(password, member.getSalt());
            }
            info = new SimpleAuthenticationInfo(member, password, ByteSource.Util.bytes(member.getSalt()), getName());
        }
        return info;
    }

    /**
     * 当用户进行访问链接时的授权方法
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/1 12:38
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        credentialsMatcher.setHashIterations(1);
        setCredentialsMatcher(credentialsMatcher);
    }
}
