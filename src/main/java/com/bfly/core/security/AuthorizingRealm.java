//package com.bfly.core.security;
//
//import com.bfly.cms.member.entity.Member;
//import com.bfly.cms.user.service.CmsUserMng;
//import com.bfly.cms.user.service.UnifiedUserMng;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authc.credential.CredentialsMatcher;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
///**
// * 自定义DB Realm
// *
// * @author andy_hulibo@163.com
// * @date 2018/12/1 21:27
// */
//@Component("authorizingRealm")
//public class AuthorizingRealm extends org.apache.shiro.realm.AuthorizingRealm {
//
//    /**
//     * 会员中心登录认证
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/12/1 11:46
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        Member user = cmsUserMng.findByUsername(token.getUsername());
//        if (user != null) {
//            Member unifiedUser = unifiedUserMng.findById(user.getId());
//            return new SimpleAuthenticationInfo(user.getUsername(), unifiedUser.getPassword(), getName());
//        }
//        return null;
//    }
//
//    /**
//     * 当用户进行访问链接时的授权方法
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/12/1 12:38
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        return new SimpleAuthorizationInfo();
//    }
//
//    public void removeUserAuthorizationInfoCache(String username) {
//        SimplePrincipalCollection pc = new SimplePrincipalCollection();
//        pc.add(username, super.getName());
//        super.clearCachedAuthorizationInfo(pc);
//    }
//
//    @Autowired
//    protected CmsUserMng cmsUserMng;
//    @Autowired
//    protected UnifiedUserMng unifiedUserMng;
//
//    @Override
//    @Autowired
//    @Qualifier("hashedCredentialsMatcher")
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        super.setCredentialsMatcher(credentialsMatcher);
//    }
//}
