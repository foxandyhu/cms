package com.bfly.core.web;

public class ResponseCode {
	/**
	 * 登陆状态
	 */
	public static final String API_CODE_USER_STATUS_LOGIN="\"1\"";
	/**
	 * 退出状态
	 */
	public static final String API_CODE_USER_STATUS_LOGOUT="\"2\"";
	/**
	 * 超时状态
	 */
	public static final String API_CODE_USER_STATUS_OVER_TIME="\"3\"";

	/**
	 * 成功
	 */
	public static final String API_CODE_CALL_SUCCESS="\"200\"";
	public static final String G_API_CODE_CALL_SUCCESS="200";
	/**
	 * 未知错误
	 */
	public static final String API_CODE_CALL_FAIL="\"101\"";
	
	/**
	 * 缺少参数
	 */
	public static final String API_CODE_PARAM_REQUIRED="\"201\"";

	/**
	 * 参数错误
	 */
	public static final String API_CODE_PARAM_ERROR="\"202\"";

	public static final String G_API_CODE_PARAM_ERROR="202";
	/**
	 * APPID错误或被禁用
	 */
	public static final String API_CODE_APP_PARAM_ERROR="\"203\"";
	/**
	 * 签名错误
	 */
	public static final String API_CODE_SIGN_ERROR="\"204\"";
	/**
	 * 不能删除
	 */
	public static final String API_CODE_DELETE_ERROR="\"205\"";
	/**
	 * 不存在该对象
	 */
	public static final String API_CODE_NOT_FOUND="\"206\"";
	/**
	 * 用户会话错误[已退出或者未登录]
	 */
	public static final String API_CODE_SESSION_ERROR="\"207\"";
	/**
	 *重复请求API
	 */
	public static final String API_CODE_REQUEST_REPEAT="\"208\"";
	/**
	 *用户没有权限
	 */
	public static final String API_CODE_USER_NOT_HAS_PERM="\"209\"";
	/**
	 *数据引用错误
	 */
	public static final String API_CODE_DATA_INTERGER_VIOLATION="\"210\"";
	/**
	 *sql语法错误
	 */
	public static final String API_CODE_SQL_ERROR="\"211\"";

	/**
	 * 用户未找到
	 */
	public static final String API_CODE_USER_NOT_FOUND="\"301\"";
	/**
	 * 用户未登陆
	 */
	public static final String API_CODE_USER_NOT_LOGIN="\"302\"";

	/**
	 *密码错误
	 */
	public static final String API_CODE_PASSWORD_ERROR="\"304\"";
	/**
	 *用户名已存在
	 */
	public static final String API_CODE_USERNAME_EXIST="\"305\"";
	/**
	 *原密码错误
	 */
	public static final String API_CODE_ORIGIN_PWD_ERROR="\"306\"";
	/**
	 *上传错误
	 */
	public static final String API_CODE_UPLOAD_ERROR="\"307\"";
	/**
	 *订单编号已经使用
	 */
	public static final String API_CODE_ORDER_NUMBER_USED="\"308\"";
	/**
	 *订单编号错误
	 */
	public static final String API_CODE_ORDER_NUMBER_ERROR="\"309\"";
	/**
	 *订单金额不足
	 */
	public static final String API_CODE_ORDER_AMOUNT_NOT_ENOUGH="\"310\"";

	/**
	 * 用户账户未找到
	 */
	public static final String API_CODE_USER_ACCOUNT_NOT_FOUND="\"312\"";
	/**
	 * 用户余额不足
	 */
	public static final String API_CODE_USER_BALANCE_NOT_ENOUGH="\"313\"";
	/**
	 * 提现金额太小
	 */
	public static final String API_CODE_DRAW_LESS="\"314\"";

	/**
	 * 文件未找到
	 */
	public static final String API_CODE_FILE_NOT_FOUNT="\"326\"";


	
	public static final String API_CODE_ACCOUNT_DISABLED="\"339\"";
	/**
	 * 模型已存在
	 */
	public static final String API_CODE_MODEL_EXIST="\"340\"";
	/**
	 * 字段已存在
	 */
	public static final String API_CODE_FIELD_EXIST="\"341\"";
	/**
	 * 访问路径已存在
	 */
	public static final String API_CODE_ACCESSPATH_EXIST="\"342\"";
	/**
	 * 域名已存在
	 */
	public static final String API_CODE_DOMAIN_EXIST="\"343\"";
	/**
	 * 角色等级错误
	 */
	public static final String API_CODE_ROLE_LEVEL_ERROR="\"344\"";
	/**
	 * 内容模型ID已存在
	 */
	public static final String API_CODE_CONTENTTYPE_ID_EXIST="\"345\"";

	/**
	 * tag名称已存在
	 */
	public static final String API_CODE_TAG_NAME_EXIST="\"348\"";
	/**
	 * 恢复错误
	 */
	public static final String API_CODE_DB_REVERT_ERROR="\"349\"";

	/**
	 * AES128解密错误
	 */
	public static final String API_CODE_AES128_ERROR="\"351\"";
	/**
	 * 支付错误
	 */
	public static final String API_CODE_PAY_ERROR="\"352\"";
	/**
	 * 二维码错误
	 */
	public static final String API_CODE_CAPTCHA_CODE_ERROR="\"353\"";
	/**
	 * 短信发送间隔时间不足
	 */
	public static final String API_CODE_INTERVAL_NOT_ENOUGH = "\"354\"";
	/**
	 * 短信服务未设置
	 */
	public static final String API_CODE_SMS_NOT_SET = "\"355\"";
	/**
	 * 短信服务每日发送限制
	 */
	public static final String API_CODE_SMS_LIMIT ="\"356\"";
	/**
	 * 手机号已被注册或已绑定
	 */
	public static final String API_CODE_MOBILE_PHONE_EXIST= "\"357\"";
	/**
	 * 短信服务请求错误
	 */
	public static final String API_CODE_SMS_ERROR="\"358\"";
	/**
	 * 短信服务禁用
	 */
	public static final String API_CODE_SMS_IS_DISABLE="\"359\"";
	/**
	 * 用户未设置手机号
	 */
	public static final String API_CODE_MOBILE_NOT_SET = "\"360\"";
	/**
	 * 手机号不匹配
	 */
	public static final String API_CODE_MOBILE_MISMATCHING = "\"361\"";
	/**
	 * 审核失败
	 */
	public static final String API_CODE_CHECK_ERROR="\"362\"";
}
