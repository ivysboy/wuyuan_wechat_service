package com.wuyuan.wechat.controller;

import com.wuyuan.wechat.service.impl.BaseWxService;
import com.wuyuan.wechat.service.impl.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-31 13:53</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
@RestController
@RequestMapping("/api/portal")
public class WxPortalController extends AbstractWxPortalController {

    @Autowired
    private WxService wxService;

    @Override
    protected BaseWxService getWxService() {
        return wxService;
    }

}
