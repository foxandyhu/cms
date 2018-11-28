package com.bfly.cms.staticpage.dao;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.content.entity.Content;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public interface StaticPageDao {

	 int channelStatic(HttpServletRequest request, HttpServletResponse response,
			Integer siteId, Integer channelId, boolean containChild, Configuration conf, Map<String, Object> data)
			throws IOException, TemplateException;

	 void channelStatic(Channel c, boolean firstOnly, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException;

	 int contentStatic(HttpServletRequest request, HttpServletResponse response,
			Integer siteId,Integer channelId, Date start,
			Configuration conf, Map<String, Object> data) throws IOException,
			TemplateException;
	
	 boolean contentStatic(Content c, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException;

	 int contentsOfChannel(Integer channelId);

	 int childsOfChannel(Integer channelId);
}
