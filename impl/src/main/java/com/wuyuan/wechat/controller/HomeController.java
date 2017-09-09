package com.wuyuan.wechat.controller;

import com.happylifeplat.Result;
import com.happylifeplat.commons.utils.UUIDUtils;
import com.happylifeplat.messagecode.impl.WechatCode;
import com.wuyuan.wechat.config.ApolloWeChatConfig;
import com.wuyuan.wechat.model.JsapiConfig;
import com.wuyuan.wechat.service.impl.WxService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.impl.WxMpQrcodeServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-30 16:25</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
@RestController
@RequestMapping("/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    WxService wxMpService;

    @Autowired
    ApolloWeChatConfig apolloWeChatConfig;

    @RequestMapping("/jsticket")
    public String getJsapiTicket() {
        try {
            return wxMpService.getJsapiTicket();
        } catch (WxErrorException e) {
            e.printStackTrace();
            logger.error(e.getError().getErrorMsg());
        }
        return "Error";
    }

    @RequestMapping("/jsapi-config")
    public JsapiConfig getJsapiConfig(@RequestParam(value = "url", required = false) String url){
    	if(StringUtils.isEmpty(url)) {
            url = apolloWeChatConfig.getJsapiUrl();
    	}
    	logger.info("============url======"+url);
        JsapiConfig config = new JsapiConfig();

        try {
            WxJsapiSignature signature = wxMpService.createJsapiSignature(url);
            config.setAppId(signature.getAppId());
            config.setDebug(true);
            config.setSignature(signature.getSignature());
            config.setTimestamp(signature.getTimestamp());
            config.setNonceStr(signature.getNonceStr());
            config.setUrl(signature.getUrl());

        } catch (WxErrorException e) {
            e.printStackTrace();
            logger.error(e.getError().getErrorMsg());
        }
        return config;
    }

    @RequestMapping(value = "/getProviderWXQR", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result getProviderWXQR(@RequestParam String providerId) {
        if(StringUtils.isEmpty(providerId)) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        if(!UUIDUtils.isUUID(providerId)) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        String qrUrl = null;
        try {
            WxMpQrcodeService qrcodeService = new WxMpQrcodeServiceImpl(wxMpService);
            WxMpQrCodeTicket ticket1 = qrcodeService.qrCodeCreateLastTicket(providerId);
            qrUrl = qrcodeService.qrCodePictureUrl(ticket1.getTicket(), true);
        } catch (Exception e) {
            logger.error("=============get access token error: " + e.getMessage());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("QRCodeUrl", qrUrl);
        return Result.success(result);
    }

}
