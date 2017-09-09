package com.happylifeplat.wechat.handler.wangshenghuo;

import com.happylifeplat.wechat.config.ApolloWeChatConfig;
import com.happylifeplat.wechat.handler.MsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class WshMsgHandler extends MsgHandler {

  @Autowired
  private ApolloWeChatConfig wxConfig;

  @Override
  protected ApolloWeChatConfig getWxConfig() {
    return this.wxConfig;
  }

}
