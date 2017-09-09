package com.wuyuan.wechat.builder;

import com.wuyuan.wechat.service.impl.BaseWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
public abstract class AbstractBuilder {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public abstract WxMpXmlOutMessage build(String content,
      WxMpXmlMessage wxMessage, BaseWxService service) ;
}
