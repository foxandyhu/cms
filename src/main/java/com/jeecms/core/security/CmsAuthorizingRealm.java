package com.jeecms.core.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.web.CmsThreadVariable;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.UnifiedUser;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.manager.UnifiedUserMng;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 自定义DB Realm
 * 
 */
@Component("authorizingRealm")
public class CmsAuthorizingRealm extends AuthorizingRealm {

	/**
	 * 登录认证
	 */
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		CmsUser user = cmsUserMng.findByUsername(token.getUsername());
		if (user != null) {
			UnifiedUser unifiedUser = unifiedUserMng.findById(user.getId());
			return new SimpleAuthenticationInfo(user.getUsername(), unifiedUser.getPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权
	 */
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		CmsUser user = cmsUserMng.findByUsername(username);
		CmsSite site=CmsThreadVariable.getSite();
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		if (user != null) {
			Set<String>viewPermissionSet=new HashSet<String>();
			Set<String> perms = user.getPerms(site.getId(),viewPermissionSet);
			if (!CollectionUtils.isEmpty(perms)) {
				// 权限加入AuthorizationInfo认证对象
				auth.setStringPermissions(perms);
			}
		}
		return auth;
	}
	
	public void removeUserAuthorizationInfoCache(String username){
		  SimplePrincipalCollection pc = new SimplePrincipalCollection();
		  pc.add(username, super.getName()); 
		  super.clearCachedAuthorizationInfo(pc);
	}

	@Autowired
	protected CmsUserMng cmsUserMng;
	@Autowired
	protected UnifiedUserMng unifiedUserMng;

	@Override
	@Autowired
	@Qualifier("hashedCredentialsMatcher")
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher(credentialsMatcher);
	}
}
