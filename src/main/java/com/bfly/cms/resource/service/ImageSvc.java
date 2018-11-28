package com.bfly.cms.resource.service;

import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;

/**
 * @author Tom
 */
public interface ImageSvc {

	/**
	 * 抓取远程图片返回本地地址
	 * @param imgUrl 远程图片URL
	 * @return
	 */
	 String crawlImg(String imgUrl,CmsSite site);
	
	 String crawlImg(String imgUrl,String ctx,boolean uploadToDb,
			String dbFileUri,Ftp ftp,CmsOss oss,String uploadPath);
}
