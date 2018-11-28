package com.bfly.cms.system.service;

import java.sql.SQLException;
import java.util.List;

import com.bfly.cms.system.entity.CmsField;

public interface CmsMysqlDataBackMng {
	 List<String> listTabels(String catalog);

	 List<CmsField> listFields(String tablename);

	 List<String> listDataBases();

	 String createTableDDL(String tablename);

	 List<Object[]> createTableData(String tablename);
	
	 String getDefaultCatalog()throws SQLException;

	 Boolean executeSQL(String sql);
}