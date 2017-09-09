package com.wuyuan.wechat.model.result;

import io.swagger.annotations.ApiModelProperty;

public class WechatPublicUserDto {

	@ApiModelProperty("id")
	private String id;
	
	@ApiModelProperty("用户id")
	private String userId;
	
	@ApiModelProperty("微信openId")
	private String openId;

	@ApiModelProperty("公众号appid")
	private String appId;

	@ApiModelProperty("手机号码")
	private String phone;

	public WechatPublicUserDto() {
	}
	
	public WechatPublicUserDto(String openId, String appId) {
		this.openId = openId;
		this.appId = appId;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
