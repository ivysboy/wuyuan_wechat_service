package com.wuyuan.wechat.listener;

import com.google.gson.Gson;
import com.wuyuan.wechat.builder.TemplateMsgBuilder;
import com.wuyuan.wechat.config.ApolloWeChatConfig;
import com.wuyuan.wechat.service.impl.WxService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
@Component
public class WorkflowOrderActionMqListener {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowOrderActionMqListener.class);
    private static Gson gson = new Gson();

    @Autowired
    private WxService wxMpService;

    @Autowired
    private TemplateMsgBuilder templateMsgBuilder;

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    @JmsListener(destination = "com.wuyuan.payment.complete.v2")
    public void receiveQueue(String notice) {
        logger.info("=================order action: " + notice);
//        JSONObject jsn = JSON.parseObject(notice);
        logger.info("----------- 支付回调begin -----------" + notice);
//        String paynumber = jsn.getString("payNumber");

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setToUser("olHAy0zLKlDitDMLfXSUuT9rysAI");
        templateMessage.setTemplateId(apolloWeChatConfig.getTemplateId());
        templateMessage.setUrl("http://www.qq.com");
        templateMessage.getData().add(new WxMpTemplateData("first", "标题", "#000000"));
        templateMessage.getData().add(new WxMpTemplateData("keyword1", "状态", "#000000"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("----------- 支付回调 end -----------");
    }
}
