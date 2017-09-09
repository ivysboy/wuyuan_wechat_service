package com.wuyuan.wechat.model;

import io.swagger.annotations.ApiModelProperty;

public class WechatPayDto {
	
	@ApiModelProperty("微信openId")
	private String openId;

	@ApiModelProperty("手机号码")
	private String phone;

	@ApiModelProperty("订单id")
	private String ordersId;

	@ApiModelProperty("用户ip")
	private String userIp;

	@ApiModelProperty("payNumber")
	private String payNumber;
	
	private String device;
	
	@ApiModelProperty("支付后的前端回调页面")
	private String frontEndCallbackUrl;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getFrontEndCallbackUrl() {
		return frontEndCallbackUrl;
	}

	public void setFrontEndCallbackUrl(String frontEndCallbackUrl) {
		this.frontEndCallbackUrl = frontEndCallbackUrl;
	}



}
