package com.wuyuan.wechat.handler;

import java.util.Map;

import com.wuyuan.wechat.config.ApolloWeChatConfig;
import org.springframework.stereotype.Component;


import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
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
public class KfSessionHandler extends AbstractHandler{

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
      Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
    //TODO 对会话做处理
    return null;
  }

  @Override
  protected ApolloWeChatConfig getWxConfig() {
    return null;
  }

}
