package com.bfly.core.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 数据持久层父类接口
 * @author andy_hulibo@163.com
 * @date 2018/12/6 18:15
 */
@NoRepositoryBean
public interface IBaseDao<T,ID> extends JpaRepository<T,ID> ,JpaSpecificationExecutor<T>{
}
