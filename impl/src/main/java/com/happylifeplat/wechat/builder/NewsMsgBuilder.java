package com.happylifeplat.wechat.builder;

import com.happylifeplat.wechat.service.impl.BaseWxService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;

/**
 * Created by xuwuyuan on 2017/9/6.
 */
public class NewsMsgBuilder extends AbstractBuilder {
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, BaseWxService service) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription(content);
        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().addArticle(item).toUser(wxMessage.getFromUser()).fromUser(wxMessage.getToUser()).build();

        return m;
    }

    public WxMpXmlOutMessage customBuild(String title, String content, String url, WxMpXmlMessage wxMessage, BaseWxService service) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription(content);
        item.setTitle(title);
        item.setUrl(url);

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().addArticle(item).toUser(wxMessage.getFromUser()).fromUser(wxMessage.getToUser()).build();

        return m;
    }
}
