package com.happylifeplat.wechat.handler.wangshenghuo;

import com.happylifeplat.wechat.config.ApolloWeChatConfig;
import com.happylifeplat.wechat.handler.SubscribeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-30 16:25</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
@Component
public class WshSubscribeHandler extends SubscribeHandler {

  @Autowired
  private ApolloWeChatConfig wxConfig;

  @Override
  protected ApolloWeChatConfig getWxConfig() {
    return this.wxConfig;
  }

  @Override
  protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) {
    return null;
  }

}
