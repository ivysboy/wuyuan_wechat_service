package com.wuyuan.wechat.domain;

import io.swagger.annotations.ApiModelProperty;

public class WechatPublicUser {

	@ApiModelProperty("id")
	private String id;
	
	@ApiModelProperty("用户id")
	private String userId;
	
	@ApiModelProperty("微信openId")
	private String openId;

	@ApiModelProperty("公众号appid")
	private String appId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
