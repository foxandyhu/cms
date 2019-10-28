package com.bfly.cms.system.service.impl;

import com.bfly.cms.member.entity.LoginConfig;
import com.bfly.cms.member.service.IMemberLoginConfigService;
import com.bfly.cms.system.entity.EmailMessage;
import com.bfly.cms.system.entity.EmailProvider;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * 忘记密码邮件服务
 *
 * @author andy_hulibo@163.com
 * @date 2019/10/21 15:27
 */
@Service("forgetPwdEmailServiceImpl")
public class ForgetPwdEmailServiceImpl extends AbstractEmailServiceImpl {

    @Autowired
    private IMemberLoginConfigService loginConfigService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void send(Map<String, Object> params, String... sendTo) throws Exception {
        super.send(params, sendTo);
    }

    /**
     * 登录配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 15:58
     */
    private LoginConfig getLoginConfig() {
        LoginConfig config = loginConfigService.getLoginConfig();
        Assert.notNull(config, "邮件登录服务尚未配置!");
        return config;
    }

    @Override
    public EmailProvider getEmailProvider() {
        LoginConfig config = getLoginConfig();
        EmailProvider provider = config.getEmailProvider();
        Assert.notNull(config, "邮件登录服务尚未配置!");
        return provider;
    }

    @Override
    public EmailMessage getMessage(Map<String, Object> params, String... sendTo) throws Exception {
        LoginConfig config = getLoginConfig();
        EmailProvider provider = config.getEmailProvider();
        EmailMessage message = new EmailMessage();
        message.setPriority(3);
        message.setSenderName(provider.getPersonal());
        message.setSenderAddress(provider.getUserName());
        message.setSubject(config.getRetrievePwdTitle());

        message.setReplyAddress(provider.getUserName());
        message.setReceiverAddress(sendTo);

        FreeMarkerConfigurer freeMarkerConfigurer = applicationContext.getBean(FreeMarkerConfigurer.class);

        Template tpl = new Template("forgetPwdTpl", new StringReader(config.getRetrievePwdText()), freeMarkerConfigurer.getConfiguration());

        StringWriter writer = new StringWriter();
        tpl.process(params, writer);
        String text = writer.toString();
        message.setContent(text);

        return message;
    }
}
