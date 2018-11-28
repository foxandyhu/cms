package com.bfly.cms.resource.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.resource.dao.CmsFileDao;
import com.bfly.cms.resource.entity.CmsFile;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.resource.service.CmsFileMng;
import com.bfly.common.hibernate4.Updater;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsFileMngImpl implements CmsFileMng {
	
	@Override
    public CmsFile deleteById(Integer id) {
		return dao.deleteById(id);
	}
	
	@Override
    public CmsFile deleteByPath(String path){
		return dao.deleteByPath(path);
	}
	
	@Override
    public void deleteByContentId(Integer contentId){
		 dao.deleteByContentId(contentId);
	}


	@Override
    public CmsFile findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public CmsFile findByPath(String path) {
		return dao.findByPath(path);
	}

	@Override
    public List<CmsFile> getList(Boolean valid) {
		return dao.getList(valid);
	}

	@Override
    public CmsFile save(CmsFile bean) {
		return dao.save(bean);
	}

	@Override
    public void saveFileByPath(String filepath, String name, Boolean valid) {
		CmsFile attFile=new CmsFile();
		attFile.setFilePath(filepath);
		attFile.setFileName(name);
		attFile.setFileIsvalid(valid);
		save(attFile);
	}

	@Override
    public void updateFileByPath(String path, Boolean valid, Content c) {
		CmsFile file;
		file=findByPath(path);
		if(file!=null){
			file.setContent(c);
			file.setFileIsvalid(valid);
			update(file);
		}
	}
	@Override
    public void updateFileByPaths(String[] attachmentPaths, String[]picPaths, String mediaPath,
                                  String titleImg, String typeImg, String contentImg, Boolean valid, Content c){
		//处理附件有效性
		if(attachmentPaths!=null){
			for(String att:attachmentPaths){
				updateFileByPath(att, valid, c);
			}
		}
		//处理图片集
		if(picPaths!=null){
			for(String pic:picPaths){
				updateFileByPath(pic, valid, c);
			}
		}
		//处理多媒体
		if(StringUtils.isNotBlank(mediaPath)){
			updateFileByPath(mediaPath, valid, c);
		}
		//标题图
		if(StringUtils.isNotBlank(titleImg)){
			updateFileByPath(titleImg, valid, c);
		}
		//类型图
		if(StringUtils.isNotBlank(typeImg)){
			updateFileByPath(typeImg, valid, c);
		}
		//内容图
		if(StringUtils.isNotBlank(contentImg)){
			updateFileByPath(contentImg, valid, c);
		}
	}

	@Override
    public CmsFile update(CmsFile bean) {
		Updater<CmsFile> updater = new Updater<CmsFile>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Autowired
	private CmsFileDao dao;
}