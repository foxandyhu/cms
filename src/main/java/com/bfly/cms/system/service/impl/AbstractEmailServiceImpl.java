package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.EmailMessage;
import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.cms.system.service.IEmailService;
import com.bfly.core.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 忘记密码邮件服务
 *
 * @author andy_hulibo@163.com
 * @date 2019/10/21 15:27
 */
public abstract class AbstractEmailServiceImpl implements IEmailService {

    private Logger logger = LoggerFactory.getLogger(AbstractEmailServiceImpl.class);

    @Autowired
    @Qualifier("taskScheduler")
    private ThreadPoolTaskScheduler taskExecutor;

    @Override
    public void send(Map<String, Object> params, String... sendTo) throws Exception {
        Assert.isTrue(ArrayUtils.isNotEmpty(sendTo), "收件人地址为空!");

        JavaMailSenderImpl mailSender = getMailSender(getEmailProvider());
        EmailMessage message = getMessage(params, sendTo);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, Constants.ENCODEING_UTF8);
        helper.setPriority(message.getPriority());
        helper.setFrom(message.getSenderAddress(), message.getSenderName());
        if (StringUtils.hasLength(message.getReplyAddress())) {
            helper.setReplyTo(message.getReplyAddress());
        }
        if (ArrayUtils.isNotEmpty(message.getBccAddress())) {
            helper.setBcc(message.getBccAddress());
        }
        if (ArrayUtils.isNotEmpty(message.getCcAddress())) {
            helper.setCc(message.getCcAddress());
        }
        helper.setSubject(message.getSubject());
        helper.setText(message.getContent(), true);
        helper.setTo(message.getReceiverAddress());
        if (ArrayUtils.isNotEmpty(message.getAttachments())) {
            File file;
            for (String path : message.getAttachments()) {
                //添加附件
                file = new File(path);
                helper.addAttachment(file.getName(), file);
            }
        }
        taskExecutor.execute(() -> doSend(mailSender, helper, message));
    }

    private void doSend(JavaMailSender mailSender, MimeMessageHelper helper, EmailMessage message) {
        try {
            message.setCount(message.getCount() + 1);
            if (message.getCount() <= RETRY_COUNT) {
                mailSender.send(helper.getMimeMessage());
                logger.info("邮件<" + message.getReceiverAddress()[0] + ">发送成功!");
            }
        } catch (Exception e) {
            // 发送邮件失败 向任务队列提交任务 5秒后再次执行
            logger.error("", e);
            if (message.getCount() < RETRY_COUNT) {
                logger.error("邮件<" + message.getReceiverAddress()[0] + ">发送失败,系统将自动发起下一次发送任务!");
            }
            if (message.getCount() <= RETRY_COUNT) {
                Date nextDate = DateUtils.addSeconds(new Date(), 5);
                taskExecutor.schedule(() -> doSend(mailSender, helper, message), nextDate);
            }
        }
    }

    /**
     * 得到邮件服务提供商信息
     *
     * @return EmailProvider
     * @author andy_hulibo@163.com
     * @date 2019/10/21 16:04
     */
    public abstract EmailProvider getEmailProvider();

    /**
     * 封装邮件对象
     *
     * @param params
     * @param sendTo
     * @return EmailMessage
     * @throws Exception
     * @author andy_hulibo@163.com
     * @date 2019/9/24 11:43
     */
    public abstract EmailMessage getMessage(Map<String, Object> params, String... sendTo) throws Exception;
}
