package com.wuyuan.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wuyuan.wechat.model.result.WechatPublicUserDto;
import com.wuyuan.wechat.service.IOauth2Service;
import com.wuyuan.wechat.util.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;


/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-30 16:25</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {
	private static final Logger logger = LoggerFactory.getLogger(Oauth2Controller.class);
	
	@Autowired
	private IOauth2Service oauth2Service;
	
	@RequestMapping(value="/authorize", method=RequestMethod.GET)
	@ApiOperation(value="获取授权url")
	public ResponseModel authorize(@RequestParam(value = "appId",required = false) String appId, @RequestParam("redirectUrl") String redirectUrl) {
		return oauth2Service.authorize(appId, redirectUrl);
	}

	@RequestMapping(value="/valid", method=RequestMethod.GET)
	@ApiOperation(value="验证微信号是否绑定（微信验证回调）")
	public void valid(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		logger.info("code======"+code+"======================state======"+state);
		
		ResponseModel responseModel = oauth2Service.valid(code, state);
		logger.info("responseModel======"+responseModel.getCode());
		
		if(responseModel.getCode() == 200) {
			try {
				String url = (String) responseModel.getData();
				logger.info("======url=="+url);
				response.sendRedirect(url);
				return;
			} catch (IOException e) {
				logger.debug("seneRedirect error-------"+e.getMessage());
			}
		}
	}

	@RequestMapping(value="/bind", method=RequestMethod.POST)
	@ApiOperation(value="绑定旺管家账号")
	public ResponseModel bind(@RequestBody WechatPublicUserDto wechatPublicUserDto) {
		return oauth2Service.bind(wechatPublicUserDto);
	}

}
