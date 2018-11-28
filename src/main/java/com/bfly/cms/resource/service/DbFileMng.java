package com.bfly.cms.resource.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.bfly.cms.resource.entity.DbFile;

public interface DbFileMng {
	 DbFile findById(String id);

	 String storeByExt(String path, String ext, InputStream in)
			throws IOException;

	 String storeByFilename(String filename, InputStream in)
			throws IOException;

	 File retrieve(String name) throws IOException;

	 boolean restore(String name, File file)
			throws FileNotFoundException, IOException;

	 DbFile deleteById(String id);

	 DbFile[] deleteByIds(String[] ids);
}