package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.common.StringUtil;
import com.bfly.core.Constants;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 系统邮件业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/23 20:30
 */
public interface IEmailService {

    /**
     * 邮件发送失败重发次数
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/23 21:42
     */
    int RETRY_COUNT = 3;

    String SMTP_PROTOCOL = "smtp";

    /**
     * 发送邮件
     *
     * @param params
     * @param sendTo
     * @throws Exception
     * @author andy_hulibo@163.com
     * @date 2019/9/23 20:31
     */
    void send(Map<String, Object> params, String... sendTo) throws Exception;

    /**
     * 邮件服务提供商配置
     *
     * @param provider
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/24 11:05
     */
    default JavaMailSenderImpl getMailSender(EmailProvider provider) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        Assert.hasText(provider.getUserName(), "邮件服务器缺少配置!");
        Assert.hasText(provider.getPassword(), "邮件服务器缺少配置!");
        Assert.hasText(provider.getHost(), "邮件服务器缺少配置!");

        sender.setPassword(provider.getPassword());
        sender.setUsername(provider.getUserName());
        sender.setHost(provider.getHost());
        sender.setPort(provider.getPort() <= 0 ? 25 : provider.getPort());
        sender.setDefaultEncoding(StringUtils.hasLength(provider.getEncoding()) ? provider.getEncoding() : Constants.ENCODEING_UTF8);
        sender.setProtocol((StringUtils.hasLength(provider.getProtocol()) ? provider.getProtocol() : SMTP_PROTOCOL).toLowerCase());
        return sender;
    }
}
