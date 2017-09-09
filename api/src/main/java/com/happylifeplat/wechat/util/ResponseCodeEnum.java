package com.happylifeplat.wechat.util;

/**
 * 返回状态的code的枚举类
 */
public enum ResponseCodeEnum {

	/**
	 * 200: 成功
	 */
	SUCCESS(200),

	/**
	 * 302: 重定向
	 */
	REDIRECT(302),

	/**
	 * 403：没有权限
	 */
	NO_AUTHORITY(403),

	/**
	 * 404：数据不存在
	 */
	DATA_NOT_FOUND(404),

	/**
	 * 500: 状态错误
	 */
	ERROR(500),

	/**
	 * 501: 参数错误
	 */
	PARAMS_ERROR(501),

	/**
	 * 502: 已存在
	 */
	HAS_EXIST(502),
	
	/**
	 * 503: 支付失败
	 */
	PAY_ERROR(503);

	private int code;

	private ResponseCodeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}