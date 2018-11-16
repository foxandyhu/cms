package com.jeecms.cms.entity.back;

import java.io.Serializable;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/16 9:36
 */
public class CmsConstraints implements Serializable {
    private static final long serialVersionUID = 1L;
   
    
    private String name;
    private String tableName;
    private String columnName;
    private String referencedTableName;
    private String referencedColumnName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public void setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    public void setReferencedColumnName(String referencedColumnName) {
        this.referencedColumnName = referencedColumnName;
    }
}