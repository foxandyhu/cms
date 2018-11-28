package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.CmsField;

import java.sql.SQLException;
import java.util.List;

public interface CmsMysqlDataBackDao {

    List<String> listTables(String catalog);

    List<CmsField> listFields(String tablename);

    List<String> listDataBases();

    String createTableDDL(String tablename);

    String getDefaultCatalog() throws SQLException;

    List<Object[]> createTableData(String tablename);

    Boolean executeSQL(String sql);

}