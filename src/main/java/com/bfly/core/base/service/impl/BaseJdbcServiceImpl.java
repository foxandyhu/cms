package com.bfly.core.base.service.impl;

import com.bfly.core.base.service.IBaseJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public abstract class BaseJdbcServiceImpl implements IBaseJdbcService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void executeSql(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public long getCountSql(String sql, Object... params) {
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public <T> List<T> querySql(String sql, RowMapper<T> rowMapper, Object... params) {
        return jdbcTemplate.query(sql, rowMapper, params);
    }

    @Override
    public List<Map<String, Object>> querySql(String sql, Object... params) {
        return jdbcTemplate.query(sql, new ColumnMapRowMapper(), params);
    }

    @Override
    public int updateSql(String sql) {
        return jdbcTemplate.update(sql);
    }
}
