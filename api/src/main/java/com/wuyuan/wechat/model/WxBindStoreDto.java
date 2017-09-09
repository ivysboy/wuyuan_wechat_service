package com.wuyuan.wechat.model;

import java.io.Serializable;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
public class WxBindStoreDto implements Serializable {
    private static final long serialVersionUID = -150021959113635335L;

    private String storeId;
    private String openId;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
