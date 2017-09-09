package com.wuyuan.wechat.builder;

import com.wuyuan.wechat.config.ApolloWeChatConfig;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuwuyuan on 2017/9/8.
 */
@Component
public class TemplateMsgBuilder {

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    public WxMpTemplateMessage getTemplateMessage(String openId,
                                                         String targetUrl,
                                                         List<WxMpTemplateData> dataList) {
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setTemplateId(apolloWeChatConfig.getTemplateId());
        message.setToUser(openId);
        message.setUrl(targetUrl);
        message.setData(dataList);
        return message;
    }
}
