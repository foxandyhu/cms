package com.bfly.cms.user.service.impl;

import com.bfly.cms.system.entity.Config.ConfigLogin;
import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.cms.user.dao.UnifiedUserDao;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.PwdEncoder;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.email.EmailSendTool;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.core.exception.BadCredentialsException;
import com.bfly.core.exception.UsernameNotFoundException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/2 17:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UnifiedUserMngImpl implements UnifiedUserMng {

    @Override
    public UnifiedUser passwordForgotten(Integer userId, EmailSender email, MessageTemplate tpl) {
        UnifiedUser user = findById(userId);
        String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
        user.setResetKey(uuid);
        String resetPwd = RandomStringUtils.randomNumeric(10);
        user.setResetPwd(resetPwd);
        senderEmail(user.getId(), user.getUsername(), user.getEmail(), user.getResetKey(), user.getResetPwd(), email, tpl);
        return user;
    }

    private void senderEmail(final Integer uid, final String username, final String to, final String resetKey, final String resetPwd, final EmailSender email, final MessageTemplate tpl) {
        String text = tpl.getForgotPasswordText();
        text = StringUtils.replace(text, "${uid}", String.valueOf(uid));
        text = StringUtils.replace(text, "${username}", username);
        text = StringUtils.replace(text, "${resetKey}", resetKey);
        text = StringUtils.replace(text, "${resetPwd}", resetPwd);
        EmailSendTool sendEmail = new EmailSendTool(email.getHost(), email.getPort(), email.getUsername(), email.getPassword(), to, tpl.getForgotPasswordSubject(), text, email.getPersonal(), "", "");
        sendEmail.sendEmil();
    }

    private void senderEmail(final String username, final String to, final String activationCode, final EmailSender email, final MessageTemplate tpl) throws UnsupportedEncodingException, MessagingException {
        String text = tpl.getRegisterText();
        text = StringUtils.replace(text, "${username}", username);
        text = StringUtils.replace(text, "${activationCode}", activationCode);
        EmailSendTool sendEmail = new EmailSendTool(email.getHost(), email.getPort(), email.getUsername(), email.getPassword(), to, tpl.getRegisterSubject(), text, email.getPersonal(), "", "");
        sendEmail.sendEmil();
    }

    @Override
    public UnifiedUser resetPassword(Integer userId) {
        UnifiedUser user = findById(userId);
        user.setPassword(pwdEncoder.encodePassword(user.getResetPwd()));
        user.setResetKey(null);
        user.setResetPwd(null);
        return user;
    }

    @Override
    public Integer errorRemaining(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        UnifiedUser user = getByUsername(username);
        if (user == null) {
            return null;
        }
        long now = System.currentTimeMillis();
        ConfigLogin configLogin = configMng.getConfigLogin();
        int maxErrorTimes = configLogin.getErrorTimes();
        int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
        Integer errorCount = user.getErrorCount();
        Date errorTime = user.getErrorTime();
        if (errorCount <= 0 || errorTime == null || errorTime.getTime() + maxErrorInterval < now) {
            return maxErrorTimes;
        }
        return maxErrorTimes - errorCount;
    }

    @Override
    public UnifiedUser login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException {
        UnifiedUser user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found: " + username);
        }
        if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
            updateLoginError(user.getId(), ip);
            throw new BadCredentialsException("password invalid");
        }
        if (!user.getActivation()) {
            throw new BadCredentialsException("account not activated");
        }
        updateLoginSuccess(user.getId(), ip);
        return user;
    }

    @Override
    public void updateLoginSuccess(Integer userId, String ip) {
        UnifiedUser user = findById(userId);
        Date now = new Timestamp(System.currentTimeMillis());

        user.setLoginCount(user.getLoginCount() + 1);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(now);

        user.setErrorCount(0);
        user.setErrorTime(null);
        user.setErrorIp(null);
    }

    @Override
    public void updateLoginError(Integer userId, String ip) {
        UnifiedUser user = findById(userId);
        Date now = new Timestamp(System.currentTimeMillis());
        ConfigLogin configLogin = configMng.getConfigLogin();
        int errorInterval = configLogin.getErrorInterval();
        Date errorTime = user.getErrorTime();

        user.setErrorIp(ip);
        if (errorTime == null || errorTime.getTime() + errorInterval * 60 * 1000 < now.getTime()) {
            user.setErrorTime(now);
            user.setErrorCount(1);
        } else {
            user.setErrorCount(user.getErrorCount() + 1);
        }
    }

    @Override
    public boolean usernameExist(String username) {
        return getByUsername(username) != null;
    }

    @Override
    public boolean emailExist(String email) {
        return dao.countByEmail(email) > 0;
    }

    @Override
    public UnifiedUser getByUsername(String username) {
        return dao.getByUsername(username);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UnifiedUser findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public UnifiedUser save(String username, String email, String password, String ip) {
        Date now = new Timestamp(System.currentTimeMillis());
        UnifiedUser user = new UnifiedUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pwdEncoder.encodePassword(password));
        user.setRegisterIp(ip);
        user.setRegisterTime(now);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(now);
        //不强制验证邮箱直接激活
        user.setActivation(true);
        user.init();
        dao.save(user);
        return user;
    }

    @Override
    public UnifiedUser save(String username, String email, String password, String ip, Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException {
        Date now = new Timestamp(System.currentTimeMillis());
        UnifiedUser user = new UnifiedUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pwdEncoder.encodePassword(password));
        user.setRegisterIp(ip);
        user.setRegisterTime(now);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(now);
        user.setActivation(activation);
        user.init();
        if (!activation) {
            String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
            user.setActivationCode(uuid);
            senderEmail(username, email, uuid, sender, msgTpl);
        }
        dao.save(user);
        return user;
    }

    /**
     * @see UnifiedUserMng#update(Integer, String, String)
     */
    @Override
    public UnifiedUser update(Integer id, String password, String email) {
        UnifiedUser user = findById(id);
        if (!StringUtils.isBlank(email)) {
            user.setEmail(email);
        } else {
            user.setEmail(null);
        }
        if (!StringUtils.isBlank(password)) {
            user.setPassword(pwdEncoder.encodePassword(password));
        }
        return user;
    }

    @Override
    public boolean isPasswordValid(Integer id, String password) {
        UnifiedUser user = findById(id);
        return pwdEncoder.isPasswordValid(user.getPassword(), password);
    }

    @Override
    public UnifiedUser deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public UnifiedUser[] deleteByIds(Integer[] ids) {
        UnifiedUser[] beans = new UnifiedUser[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    public UnifiedUser active(String username, String activationCode) {
        UnifiedUser bean = getByUsername(username);
        bean.setActivation(true);
        bean.setActivationCode(null);
        return bean;
    }

    @Override
    public void restPassword(UnifiedUser user) {
        Updater<UnifiedUser> updater = new Updater<>(user);
        dao.updateByUpdater(updater);
    }

    @Autowired
    private ConfigMng configMng;
    @Autowired
    private PwdEncoder pwdEncoder;
    @Autowired
    private UnifiedUserDao dao;
}