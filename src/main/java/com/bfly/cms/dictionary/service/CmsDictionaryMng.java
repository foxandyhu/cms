package com.bfly.cms.dictionary.service;

import com.bfly.cms.dictionary.entity.CmsDictionary;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * 数据字典业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:36
 */
public interface CmsDictionaryMng {

    /**
     * 获得数据字典集合
     *
     * @param pageNo    页码
     * @param pageSize  每页数据
     * @param queryType 类型
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:36
     */
    Pagination getPage(String queryType, int pageNo, int pageSize);

    /**
     * 根据类型获得数据字典集合
     *
     * @param queryType 类型
     * @return 集合数据
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:37
     */
    List<CmsDictionary> getList(String queryType);

    /**
     * 获得所有的数据字典类型
     *
     * @return 集合
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:38
     */
    List<String> getTypeList();

    /**
     * 根据ID获得数据字典
     *@param id ID
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:38
     */
    CmsDictionary findById(Integer id);

    /**
     * 新增数据字典
     *
     * @param bean 数据字典
     * @return 数据字典
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:45
     */
    CmsDictionary save(CmsDictionary bean);

    /**
     * 更新数据字典
     *
     * @param bean 数据字典
     * @return 数据字典
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:45
     */
    CmsDictionary update(CmsDictionary bean);

    /**
     * 删除数据字典
     *
     * @param id 数据字典ID
     * @return 数据字典
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:45
     */
    CmsDictionary deleteById(Integer id);

    /**
     * 批量删除数据字典
     *
     * @param ids 数据字典ID集合
     * @return 数据字典
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:45
     */
    CmsDictionary[] deleteByIds(Integer[] ids);

    /**
     * 检查是否有重复的数据字典
     *
     * @param type  类型
     * @param value 值
     * @return true存在 false不存在
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:46
     */
    boolean dicDeplicateValue(String value, String type);
}