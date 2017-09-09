package com.wuyuan.wechat.service;

import com.wuyuan.wechat.model.WxBindStoreDto;
import com.wuyuan.wechat.model.result.WechatPublicUserDto;
import com.wuyuan.wechat.util.ResponseModel;

public interface IOauth2Service {

	/**
	 * 获取授权url
	 * 
	 * @param appId, redirectUrl
	 * @return
	 */
	ResponseModel authorize(String appId, String redirectUrl);

	/**
	 * 验证微信号是否绑定（微信验证回调）
	 * 
	 * @param code, state
	 * @return
	 */
	ResponseModel valid(String code, String state);

	/**
	 * 绑定旺管家账号
	 * 
	 * @param wechatPublicUserDto
	 * @return
	 */
	ResponseModel bind(WechatPublicUserDto wechatPublicUserDto);

	/**
	 * 绑定供应商id
	 * @param wechatPublicUserDto
	 * @return
	 */
	ResponseModel bindProvider(WechatPublicUserDto wechatPublicUserDto);

	/**
	 * 绑定店铺id
	 * @param bindStoreDto
	 * @return
	 */
	ResponseModel bindStore(WxBindStoreDto bindStoreDto);

}
