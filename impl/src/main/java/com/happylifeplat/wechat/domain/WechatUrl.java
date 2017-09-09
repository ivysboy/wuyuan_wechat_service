package com.happylifeplat.wechat.domain;

public class WechatUrl {
	private String id;
	
	private String redirectUrl;

	public WechatUrl() {
	}
	
	public WechatUrl(String id, String redirectUrl) {
		this.id = id;
		this.redirectUrl = redirectUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
