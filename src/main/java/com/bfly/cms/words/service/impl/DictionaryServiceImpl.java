package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.IDictionaryDao;
import com.bfly.cms.words.entity.Dictionary;
import com.bfly.cms.words.service.IDictionaryService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:11
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, Integer> implements IDictionaryService {

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.save(dictionary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.edit(dictionary);
    }

    /**
     * 检查是否存在相同的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:34
     */
    private boolean checkExist(Dictionary dictionary) {
        Dictionary dic = get(new HashMap<String, Object>(3) {
            private static final long serialVersionUID = 6340025449740664103L;

            {
                put("name", dictionary.getName().trim());
                put("value", dictionary.getValue());
                put("type", dictionary.getType().trim());
            }
        });
        return dic != null && (dic.getId() != dictionary.getId());
    }

    @Override
    public List<String> getTypes() {
        return dictionaryDao.getTypes();
    }
}
