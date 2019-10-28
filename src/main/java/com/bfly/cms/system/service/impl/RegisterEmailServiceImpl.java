package com.bfly.cms.system.service.impl;

import com.bfly.cms.member.entity.RegistConfig;
import com.bfly.cms.member.service.IMemberRegistConfigService;
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
 * 会员注册邮件服务
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/24 11:00
 */
@Service("registerEmailServiceImpl")
public class RegisterEmailServiceImpl extends AbstractEmailServiceImpl {

    @Autowired
    private IMemberRegistConfigService registerConfigService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void send(Map<String, Object> params, String... sendTo) throws Exception {
        super.send(params, sendTo);
    }

    /**
     * 注册配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 16:02
     */
    private RegistConfig getRegisterConfig() {
        RegistConfig config = registerConfigService.getRegistConfig();
        Assert.notNull(config, "邮件注册服务尚未配置!");
        return config;
    }


    @Override
    public EmailProvider getEmailProvider() {
        RegistConfig config = getRegisterConfig();
        EmailProvider provider = config.getEmailProvider();
        Assert.notNull(config, "邮件注册服务尚未配置!");
        return provider;
    }

    @Override
    public EmailMessage getMessage(Map<String, Object> params, String... sendTo) throws Exception {
        RegistConfig config = getRegisterConfig();
        EmailProvider provider = config.getEmailProvider();
        EmailMessage message = new EmailMessage();
        message.setPriority(3);
        message.setSenderName(provider.getPersonal());
        message.setSenderAddress(provider.getUserName());
        message.setSubject(config.getRegisteTitle());

        message.setReplyAddress(provider.getUserName());
        message.setReceiverAddress(sendTo);

        FreeMarkerConfigurer freeMarkerConfigurer = applicationContext.getBean(FreeMarkerConfigurer.class);

        Template tpl = new Template("registerTpl", new StringReader(config.getRegisteText()), freeMarkerConfigurer.getConfiguration());

        StringWriter writer = new StringWriter();
        tpl.process(params, writer);
        String text = writer.toString();
        message.setContent(text);

        return message;
    }
}
