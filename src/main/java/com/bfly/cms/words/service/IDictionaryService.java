package com.bfly.cms.words.service;

import com.bfly.cms.words.entity.Dictionary;
import com.bfly.core.base.service.IBaseService;

import java.util.List;

/**
 * 数据字典业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:10
 */
public interface IDictionaryService extends IBaseService<Dictionary, Integer> {

    /**
     * 获得所有数据字典的类型
     *
     * @return 类型集合
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:31
     */
    List<String> getTypes();
}
