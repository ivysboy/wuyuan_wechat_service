package com.happylifeplat.wechat.handler;

import java.util.Map;

import com.happylifeplat.wechat.builder.NewsMsgBuilder;
import com.happylifeplat.wechat.builder.TextBuilder;
import com.happylifeplat.wechat.config.ApolloWeChatConfig;
import com.happylifeplat.wechat.model.result.WechatPublicUserDto;
import com.happylifeplat.wechat.service.IOauth2Service;
import com.happylifeplat.wechat.service.impl.BaseWxService;
import com.happylifeplat.wechat.util.ResponseCodeEnum;
import com.happylifeplat.wechat.util.ResponseModel;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-30 16:25</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
public abstract class SubscribeHandler extends AbstractHandler {

  @Autowired
  private IOauth2Service oauth2Service;

  @Autowired
  private ApolloWeChatConfig apolloWeChatConfig;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    BaseWxService weixinService = (BaseWxService) wxMpService;

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

    if (userWxInfo != null) {
      // TODO 可以添加关注用户到本地

      // 校验和绑定用户
      WechatPublicUserDto publicUserDto = new WechatPublicUserDto();
      publicUserDto.setAppId(apolloWeChatConfig.getAppid());
      publicUserDto.setOpenId(userWxInfo.getOpenId());
      publicUserDto.setUserId(wxMessage.getEventKey());
      ResponseModel responseModel = oauth2Service.bindProvider(publicUserDto);
      if(responseModel.getCode() != ResponseCodeEnum.SUCCESS.getCode()) {
        logger.error("===============用户绑定失败！");
        return new TextBuilder().build("供应商用户绑定失败, 请联系客服处理",wxMessage, weixinService);
      }
    }

    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }

    try {
      return new NewsMsgBuilder().customBuild("感谢关注碧桂园旺管家", "感谢关注，请设置店铺", "http://www.qq.com", wxMessage, weixinService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   */
  protected abstract WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception;

}
