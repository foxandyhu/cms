package com.bfly.cms.manager.assist.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsMysqlDataBackDao;
import com.bfly.cms.entity.back.CmsField;
import com.bfly.cms.manager.assist.CmsMysqlDataBackMng;

@Service
@Transactional
public class CmsMysqlDataBackMngImpl implements CmsMysqlDataBackMng {

	@Override
    @Transactional(readOnly = true)
	public String createTableDDL(String tablename) {
		return dao.createTableDDL(tablename);
	}

	@Override
    @Transactional(readOnly = true)
	public List<Object[]> createTableData(String tablename) {
		return dao.createTableData(tablename);
	}

	@Override
    @Transactional(readOnly = true)
	public List<CmsField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Override
    @Transactional(readOnly = true)
	public List<String> listTabels(String catalog) {
		return dao.listTables(catalog);
	}

	@Override
    @Transactional(readOnly = true)
	public List<String> listDataBases() {
		return dao.listDataBases();
	}

	@Override
    @Transactional(readOnly = true)
	public String getDefaultCatalog() throws SQLException {
		return dao.getDefaultCatalog();
	}
	
	@Override
    public void setDefaultCatalog(String catalog) throws SQLException{
		 dao.setDefaultCatalog(catalog);
	}

	@Override
    public Boolean executeSQL(String sql) {
		return dao.executeSQL(sql);
	}

	@Autowired
	private CmsMysqlDataBackDao dao;

}