package com.bfly.cms.staticpage.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.siteconfig.entity.CmsSite;

import freemarker.template.TemplateException;

public interface StaticPageSvc {
	 int content(HttpServletRequest request, HttpServletResponse response, Integer siteId, Integer channelId, Date start)
			throws IOException, TemplateException;

	 boolean content(Content content) throws IOException, TemplateException;

	 void contentRelated(Content content) throws IOException,
			TemplateException;
	
	 void contentRelated(Integer contentId) throws IOException,
	TemplateException;

	 void deleteContent(Content content) throws IOException, TemplateException;

	 int channel(HttpServletRequest request, HttpServletResponse response, Integer siteId, Integer channelId, boolean containChild)
			throws IOException, TemplateException;

	 void channel(Channel channel, boolean firstOnly) throws IOException,
			TemplateException;

	 void deleteChannel(Channel channel);
	
	 void index(Integer siteId) throws IOException, TemplateException;

	 void index(CmsSite site) throws IOException, TemplateException;

	 void index(CmsSite site, String tpl, Map<String, Object> data,boolean mobile)
			throws IOException, TemplateException;

	 boolean deleteIndex(CmsSite site);
}
