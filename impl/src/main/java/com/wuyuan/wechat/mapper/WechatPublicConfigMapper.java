package com.wuyuan.wechat.mapper;

public interface WechatPublicConfigMapper {

	/**
	 * 查询appId是否存在
	 * 
	 * @param appId
	 * @return
	 */
	int getAppIdCount(String appId);

	/**
	 * 通过appId查询appSecret
	 * 
	 * @param appId
	 * @return
	 */
	String getAppSecretByAppId(String appId);

	/**
	 * 查询appId
	 * 
	 * @return
	 */
	String getAppId();

}
