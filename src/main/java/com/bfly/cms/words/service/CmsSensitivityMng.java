package com.bfly.cms.words.service;

import java.util.List;

import com.bfly.cms.words.entity.CmsSensitivity;

public interface CmsSensitivityMng {

	 String replaceSensitivity(String s);
	
	 boolean haveSensitivity(String... arrays);

	 List<CmsSensitivity> getList(boolean cacheable);

	 CmsSensitivity findById(Integer id);

	 CmsSensitivity save(CmsSensitivity bean);

	 void updateEnsitivity(Integer[] ids, String[] searchs,
			String[] replacements);

	 CmsSensitivity deleteById(Integer id);

	 CmsSensitivity[] deleteByIds(Integer[] ids);
}