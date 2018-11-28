package com.bfly.cms.system.service;

import com.bfly.cms.system.entity.Config;
import com.bfly.cms.system.entity.Config.ConfigLogin;
import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.MessageTemplate;

import java.util.Map;

/**
 * 系统配置类业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:02
 */
public interface ConfigMng {

    /**
     * 获得所有的配置信息
     *
     * @return 配置信息
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:05
     */
    Map<String, String> getMap();

    /**
     * 获得登录配置信息
     *
     * @return 登录配置对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:00
     */
    ConfigLogin getConfigLogin();

    /**
     * 获得邮件配置信息
     *
     * @return 邮件配置
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:06
     */
    EmailSender getEmailSender();

    /**
     * 获得忘记密码的消息模板对象
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:06
     */
    MessageTemplate getForgotPasswordMessageTemplate();

    /**
     * 获得注册消息模板对象
     *
     * @return 消息模板
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:09
     */
    MessageTemplate getRegisterMessageTemplate();

    /**
     * 根据配置属性key获得对应的属性值
     *
     * @param id 配置属性key
     * @return 值
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:11
     */
    String getValue(String id);

    /**
     * 保存或更新配置信息
     *
     * @param map 配置信息
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:12
     */
    void updateOrSave(Map<String, String> map);

    /**
     * 保存或更新配置信息
     *
     * @param key   配置属性key
     * @param value 配置属性值
     * @return 配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:12
     */
    Config updateOrSave(String key, String value);

    /**
     * 删除配置属性
     *
     * @param id 配置属性key
     * @return 配置属性
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:13
     */
    Config deleteById(String id);

    /**
     * 批量删除配置属性
     *
     * @param ids 配置属性key
     * @return 配置属性集合
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:13
     */
    Config[] deleteByIds(String[] ids);
}