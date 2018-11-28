package com.bfly.cms.user.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.BadCredentialsException;
import com.bfly.core.exception.UsernameNotFoundException;
import com.bfly.cms.user.entity.UnifiedUser;

public interface UnifiedUserMng {
	/**
	 * 忘记密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param email
	 *            发送者邮件信息
	 * @param tpl
	 *            邮件模板。内容模板可用变量${uid}、${username}、${resetKey}、${resetPwd}。
	 * @return
	 */
	 UnifiedUser passwordForgotten(Integer userId, EmailSender email,
			MessageTemplate tpl);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	 UnifiedUser resetPassword(Integer userId);

	 Integer errorRemaining(String username);

	 UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException;

	 boolean usernameExist(String username);

	 boolean emailExist(String email);

	 UnifiedUser getByUsername(String username);

	 List<UnifiedUser> getByEmail(String email);

	 Pagination getPage(int pageNo, int pageSize);

	 UnifiedUser findById(Integer id);

	 UnifiedUser save(String username, String email, String password,
			String ip);
	
	 UnifiedUser save(String username, String email, String password,
			String ip, Boolean activation, EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException;

	/**
	 * 修改邮箱和密码
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            未加密密码。如果为null或空串则不修改。
	 * @param email
	 *            电子邮箱。如果为空串则设置为null。
	 * @return
	 */
	 UnifiedUser update(Integer id, String password, String email);

	/**
	 * 密码是否正确
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            未加密密码
	 * @return
	 */
	 boolean isPasswordValid(Integer id, String password);

	 UnifiedUser deleteById(Integer id);

	 UnifiedUser[] deleteByIds(Integer[] ids);
	
	 UnifiedUser active(String username, String activationCode);

	 UnifiedUser activeLogin(UnifiedUser user, String ip);
	
	 void updateLoginError(Integer userId, String ip);
	
	 void updateLoginSuccess(Integer userId, String ip);

	 void restPassword(UnifiedUser user);
}