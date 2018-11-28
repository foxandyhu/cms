package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.Config;
import com.bfly.cms.system.entity.Config.ConfigEmailSender;
import com.bfly.cms.system.entity.Config.ConfigLogin;
import com.bfly.cms.system.entity.Config.ConfigMessageTemplate;
import com.bfly.cms.system.entity.EmailSender;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.cms.system.dao.ConfigDao;
import com.bfly.cms.system.service.ConfigMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 17:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigMngImpl implements ConfigMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Map<String, String> getMap() {
        List<Config> list = dao.getList();
        Map<String, String> map = new HashMap<>(list.size());
        for (Config config : list) {
            map.put(config.getId(), config.getValue());
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String getValue(String id) {
        Config entity = dao.findById(id);
        if (entity != null) {
            return entity.getValue();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ConfigLogin getConfigLogin() {
        return ConfigLogin.create(getMap());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public EmailSender getEmailSender() {
        return ConfigEmailSender.create(getMap());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MessageTemplate getForgotPasswordMessageTemplate() {
        return ConfigMessageTemplate.createForgotPasswordMessageTemplate(getMap());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MessageTemplate getRegisterMessageTemplate() {
        return ConfigMessageTemplate.createRegisterMessageTemplate(getMap());
    }

    @Override
    public void updateOrSave(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            for (Entry<String, String> entry : map.entrySet()) {
                updateOrSave(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Config updateOrSave(String key, String value) {
        Config config = dao.findById(key);
        if (config != null) {
            config.setValue(value);
        } else {
            config = new Config();
            config.setId(key);
            config.setValue(value);
            dao.save(config);
        }
        return config;
    }

    @Override
    public Config deleteById(String id) {
        return dao.deleteById(id);
    }

    @Override
    public Config[] deleteByIds(String[] ids) {
        Config[] beans = new Config[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private ConfigDao dao;
}