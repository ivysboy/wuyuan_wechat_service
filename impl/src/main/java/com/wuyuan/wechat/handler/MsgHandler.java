package com.wuyuan.wechat.handler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.wuyuan.wechat.builder.TextBuilder;
import com.wuyuan.wechat.service.impl.BaseWxService;
import io.itit.itf.okhttp.FastHttpClient;
import org.apache.commons.lang3.StringUtils;


import me.chanjar.weixin.common.api.WxConsts;
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
public abstract class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        BaseWxService weixinService = (BaseWxService) wxMpService;

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.hasKefuOnline()) {
            return WxMpXmlOutMessage
                    .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
        }

        //
        String TOKEN = "97AAF6D2AEE22DC53F4C90D8B1BD7348";
        AtomicLong sessionId = new AtomicLong(0);
        String query = wxMessage.getContent();

        String json = null;
        String content = "回复信息内容";
        try {
            if (query != null && query.length() > 0) {
                if (!query.endsWith("？")) {
                    query += "？";
                }
            }
            json = FastHttpClient.post().url("http://www.yige.ai/v1/query").
                    addParams("token", TOKEN).
                    addParams("reset_state", "1").
                    addParams("session_id", "" + sessionId.incrementAndGet()).
                    addParams("query", query).build().execute().string();
            AIQueryResp resp = JSONUtil.fromJson(json, AIQueryResp.class);
            if (!resp.status.code.equals("200")) {
                content = "[微笑]您说的我不明白";
            }
            content = resp.answer;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            content = "[微笑]您说的我不明白";
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("query {} json:{}", query, json);
            }
        }

        //TODO 组装回复消息

        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
