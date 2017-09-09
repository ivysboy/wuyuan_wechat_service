package com.wuyuan.wechat.domain;

import io.swagger.annotations.ApiModelProperty;

public class WechatPublicConfig {
	@ApiModelProperty("公众号appid")
	private String appId;
	
	@ApiModelProperty("公众号密钥")
	private String appSecret;
	
	@ApiModelProperty("公众号通讯口令")
	private String token;

	public WechatPublicConfig() {
	}

	public WechatPublicConfig(String appId, String token) {
		this.appId = appId;
		this.token = token;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
