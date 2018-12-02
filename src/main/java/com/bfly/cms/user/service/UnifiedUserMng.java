package com.bfly.cms.user.service;

import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.BadCredentialsException;
import com.bfly.core.exception.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/2 17:11
 */
public interface UnifiedUserMng {

    /**
     * 忘记密码
     *
     * @param userId 用户ID
     * @param email  发送者邮件信息
     * @param tpl    邮件模板。内容模板可用变量${uid}、${username}、${resetKey}、${resetPwd}。
     * @return
     * @author andy_hulibo@163.com
     * @date 2018/12/2 17:12
     */
    UnifiedUser passwordForgotten(Integer userId, EmailSender email, MessageTemplate tpl);

    /**
     * 重置密码
     *
     * @param userId
     * @return
     * @author andy_hulibo@163.com
     * @date 2018/12/2 17:12
     */
    UnifiedUser resetPassword(Integer userId);

    Integer errorRemaining(String username);

    UnifiedUser login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;

    boolean usernameExist(String username);

    boolean emailExist(String email);

    UnifiedUser getByUsername(String username);

    Pagination getPage(int pageNo, int pageSize);

    UnifiedUser findById(Integer id);

    UnifiedUser save(String username, String email, String password, String ip);

    UnifiedUser save(String username, String email, String password, String ip, Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException;

    /**
     * 修改邮箱和密码
     *
     * @param id       用户ID
     * @param password 未加密密码。如果为null或空串则不修改。
     * @param email    电子邮箱。如果为空串则设置为null。
     * @return
     */
    UnifiedUser update(Integer id, String password, String email);

    /**
     * 密码是否正确
     *
     * @param id       用户ID
     * @param password 未加密密码
     * @return
     */
    boolean isPasswordValid(Integer id, String password);

    UnifiedUser deleteById(Integer id);

    UnifiedUser[] deleteByIds(Integer[] ids);

    UnifiedUser active(String username, String activationCode);

    void updateLoginError(Integer userId, String ip);

    void updateLoginSuccess(Integer userId, String ip);

    void restPassword(UnifiedUser user);
}