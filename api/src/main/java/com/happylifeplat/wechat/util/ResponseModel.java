package com.happylifeplat.wechat.util;

import java.io.Serializable;

import com.happylifeplat.plugin.mybatis.pager.PageParameter;

public class ResponseModel implements Serializable {

	private static final long serialVersionUID = 3983878601515976713L;

	private int code;

	private String msg;

	private PageParameter page;

	private String orderBy;

	private Object data;

	public ResponseModel() {
		super();
	}

	/**
	 * 
	 * @param code 请使用 ResponseCodeEnum 中的code，如果枚举类中没有请自行添加
	 * @param msg
	 */

	public ResponseModel(ResponseCodeEnum code, String msg) {
		super();
		this.code = code.getCode();
		this.msg = msg;
	}

	public ResponseModel(ResponseCodeEnum code, String msg, Object data) {
		super();
		this.code = code.getCode();
		this.msg = msg;
		this.data = data;
	}

	public ResponseModel(ResponseCodeEnum code, String msg, Object data, PageParameter page, String orderBy) {
		super();
		this.code = code.getCode();
		this.msg = msg;
		this.data = data;
		this.page = page;
		this.orderBy = orderBy;
	}

	public PageParameter getPage() {
		return page;
	}

	public void setPage(PageParameter page) {
		this.page = page;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
