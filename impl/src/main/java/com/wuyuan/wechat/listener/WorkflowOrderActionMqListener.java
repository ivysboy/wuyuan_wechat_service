package com.wuyuan.wechat.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wuyuan.wechat.builder.TemplateMsgBuilder;
import com.wuyuan.wechat.config.ApolloWeChatConfig;
import com.wuyuan.wechat.service.impl.WxService;
import me.chanjar.weixin.mp.api.impl.WxMpTemplateMsgServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
@Component
public class WorkflowOrderActionMqListener {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowOrderActionMqListener.class);
    private static Gson gson = new Gson();

    @Autowired
    private WxService wxService;

    @Autowired
    private TemplateMsgBuilder templateMsgBuilder;

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    @JmsListener(destination = "com.happylifeplat.payment.complete.v2")
    public void receiveQueue(String notice) {
        logger.info("=================order action: " + notice);
        JSONObject jsn = JSON.parseObject(notice);
        logger.info("----------- 支付回调begin -----------" + notice);
        String paynumber = jsn.getString("payNumber");

        List<WxMpTemplateData> data = new ArrayList<>();

        WxMpTemplateData first = new WxMpTemplateData();
        first.setName("first");
        first.setValue("请查看新订单");
        data.add(first);

        WxMpTemplateData keyword1 = new WxMpTemplateData();
        first.setName("keyword1");
        first.setValue("支付成功");
        data.add(keyword1);

        WxMpTemplateMessage message = templateMsgBuilder.getTemplateMessage("olHAy0zLKlDitDMLfXSUuT9rysAI",
                "http://www.qq.com", data);
        try {
            WxMpTemplateMsgServiceImpl templateMsgService = new WxMpTemplateMsgServiceImpl(wxService);
            templateMsgService.sendTemplateMsg(message);
        } catch (Exception e) {
            logger.error("==========send template msg error : " + e.getMessage());
        }

        logger.info("----------- 支付回调 end -----------");
    }
}
