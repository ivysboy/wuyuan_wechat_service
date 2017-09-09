package com.wuyuan.wechat.mapper;

import com.wuyuan.wechat.model.result.WechatPublicUserDto;

public interface WechatPublicUserMapper {

	/**
	 * 新建
	 * 
	 * @param wechatPublicUserDto
	 */
	void create(WechatPublicUserDto wechatPublicUserDto);

	/**
	 * 校验用户是否已绑定公众号
	 * 
	 * @param openId
	 * @return
	 */
	String getUserIdByOpenIdAndAppId(WechatPublicUserDto wechatPublicUserDto);

	/**
	 * 根据userId获取openId
	 * 
	 * @param userId
	 * @return
	 */
	String getOpenIdByUserId(String userId);

}
