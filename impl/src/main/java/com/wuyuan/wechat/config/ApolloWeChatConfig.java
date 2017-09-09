package com.wuyuan.wechat.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by vincent on 2017-07-07.
 */
@Component
public class ApolloWeChatConfig {
    @ApolloConfig
    private Config config;

    @Value("${authorization.enable.oauth}")
    private String oauthEnable;

    @Value("${server.userInfoUri}")
    private String oauthUserInfoUri;

    @Value("${server.redirectUri}")
    private String  redirectUri;

    @Value("${server.jsapiUrl}")
    private String jsapiUrl;


    @Value("${mp.wangshenghuo.appid}")
    private String appid;

    @Value("${mp.wangshenghuo.appsecret}")
    private String appsecret;

    @Value("${mp.wangshenghuo.token}")
    private String token;

    @Value("${mp.wangshenghuo.aesKey}")
    private String aesKey;

    @Value("${mp.wangshenghuo.templateId}")
    private String templateId;


    @ApolloConfigChangeListener("application")
    private void propertiesOnChange(ConfigChangeEvent changeEvent) {
        if (changeEvent == null) {
            return;
        }

        if (changeEvent.isChanged("authorization.enable.oauth")) {
            oauthEnable = config.getProperty("authorization.enable.oauth", oauthEnable);
        }

        if (changeEvent.isChanged("server.userInfoUri")) {
            oauthUserInfoUri = config.getProperty("server.userInfoUri", oauthUserInfoUri);
        }

        if (changeEvent.isChanged("server.redirectUri")) {
            redirectUri = config.getProperty("server.redirectUri", redirectUri);
        }

        if (changeEvent.isChanged("server.jsapiUrl")) {
            jsapiUrl = config.getProperty("server.jsapiUrl", jsapiUrl);
        }

    }


    public String getOauthEnable() {
        return oauthEnable;
    }

    public void setOauthEnable(String oauthEnable) {
        this.oauthEnable = oauthEnable;
    }

    public String getOauthUserInfoUri() {
        return oauthUserInfoUri;
    }

    public void setOauthUserInfoUri(String oauthUserInfoUri) {
        this.oauthUserInfoUri = oauthUserInfoUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getJsapiUrl() {
        return jsapiUrl;
    }

    public void setJsapiUrl(String jsapiUrl) {
        this.jsapiUrl = jsapiUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getTemplateId() {
        return templateId;
    }

}
