package com.bfly.core.base.service;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * 简单的封装 使用JDBCTemplate执行原生sql
 * 一半针对复杂的sql查询使用
 * @author andy_hulibo@163.com
 * @date 2019/8/2 11:03
 */
public interface IBaseJdbcService {

    /**
     * 简单的执行原生sql
     *
     * @param sql 原生sql
     * @author andy_hulibo@163.com
     * @date 2019/8/1 17:30
     */
    void executeSql(String sql);

    /**
     * 返回一个总数
     *
     * @param sql    原生sql
     * @param params sql参数
     * @return 返回单列值
     * @author andy_hulibo@163.com
     * @date 2019/8/1 17:42
     */
    long getCountSql(String sql, Object... params);

    /**
     * 原生sql查询
     *
     * @param rowMapper 行映射对象 可为不可null
     * @param params    sql参数 可为null
     * @param sql       原生sql
     * @return 集合对象
     * @author andy_hulibo@163.com
     * @date 2019/8/1 17:34
     */
    <T> List<T> querySql(String sql, RowMapper<T> rowMapper, Object... params);

    /**
     * 原生sql查询
     *
     * @param params sql参数 可为null
     * @param sql    原生sql
     * @return 集合对象
     * @author andy_hulibo@163.com
     * @date 2019/8/1 17:34
     */
    List<Map<String, Object>> querySql(String sql, Object... params);

    /**
     * 原生sql更新
     *
     * @param sql 原生sql
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/1 17:36
     */
    int updateSql(String sql);
}
