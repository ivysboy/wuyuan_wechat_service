package com.happylifeplat.wechat.service.impl;

import com.happylifeplat.wechat.config.ApolloWeChatConfig;
import com.happylifeplat.wechat.handler.*;
import com.happylifeplat.wechat.handler.wangshenghuo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-31 13:57</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */

@Service
public class WxService extends BaseWxService {

    @Autowired
    private ApolloWeChatConfig wxConfig;

    @Autowired
    private WshLocationHandler locationHandler;

    @Autowired
    private WshMenuHandler menuHandler;

    @Autowired
    private WshMsgHandler msgHandler;

    @Autowired
    private WshUnSubscribeHandler unSubscribeHandler;


    @Autowired
    private WshSubscribeHandler subscribeHandler;

    @Override
    protected ApolloWeChatConfig getServerConfig() {
        return this.wxConfig;
    }

    @Override
    protected MenuHandler getMenuHandler() {
        return this.menuHandler;
    }

    @Override
    protected SubscribeHandler getSubscribeHandler() {
        return this.subscribeHandler;
    }

    @Override
    protected UnsubscribeHandler getUnsubscribeHandler() {
        return this.unSubscribeHandler;
    }

    @Override
    protected AbstractHandler getLocationHandler() {
        return this.locationHandler;
    }

    @Override
    protected MsgHandler getMsgHandler() {
        return this.msgHandler;
    }

    @Override
    protected AbstractHandler getScanHandler() {
        return null;
    }
}
