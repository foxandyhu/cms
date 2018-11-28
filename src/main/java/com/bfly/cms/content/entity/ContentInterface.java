package com.bfly.cms.content.entity;

import java.util.Date;

/**
 * 内容接口
 * 
 * 定义统一的内容显示接口，便于所有内容可以采用统一的显示方式。
 */
public interface ContentInterface {
	/**
	 * 标题
	 * 
	 * @return
	 */
	 String getTit();

	/**
	 * 简短标题
	 * 
	 * @return
	 */
	 String getStit();

	/**
	 * 描述
	 * 
	 * @return
	 */
	 String getDesc();

	/**
	 * 图片地址
	 * 
	 * @return
	 */
	 String getImgUrl();

	/**
	 * 图片地址2
	 * 
	 * @return
	 */
	 String getImgUrl2();

	/**
	 * 内容链接
	 * 
	 * @return
	 */
	 String getUrl();

	/**
	 * 是否新窗口打开
	 * 
	 * @return true,false or null
	 */
	 Boolean getTarget();

	/**
	 * 标题颜色
	 * 
	 * @return
	 */
	 String getTitCol();

	/**
	 * 标题是否加粗
	 * 
	 * @return
	 */
	 boolean isTitBold();

	/**
	 * 类别名称。如栏目、留言类别、论坛板块等。
	 * 
	 * @return
	 */
	 String getCtgName();

	/**
	 * 类别URL地址。
	 * 
	 * @return
	 */
	 String getCtgUrl();

	/**
	 * 站点名称
	 * 
	 * @return
	 */
	 String getSiteName();

	/**
	 * 站点URL
	 * 
	 * @return
	 */
	 String getSiteUrl();

	/**
	 * 获得日期
	 * 
	 * @return
	 */
	 Date getDate();
}
