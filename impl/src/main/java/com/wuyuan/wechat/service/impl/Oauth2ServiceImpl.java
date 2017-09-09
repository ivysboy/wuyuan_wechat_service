package com.wuyuan.wechat.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.wuyuan.wechat.config.ApolloWeChatConfig;
import com.wuyuan.wechat.mapper.WechatPublicStoreMapper;
import com.wuyuan.wechat.model.WxBindStoreDto;
import com.wuyuan.wechat.mapper.WechatPublicUserMapper;
import com.wuyuan.wechat.mapper.WechatUrlMapper;
import com.wuyuan.wechat.model.result.WechatPublicUserDto;
import com.wuyuan.wechat.service.IOauth2Service;
import com.wuyuan.wechat.util.ResponseCodeEnum;
import com.wuyuan.wechat.util.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wuyuan.wechat.domain.WechatUrl;
import com.wuyuan.wechat.mapper.WechatPublicConfigMapper;
import com.wuyuan.wechat.util.RandomUtils;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * wechat-service
 * <p>Description: </p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Date: 2017-08-30 16:25</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author tiejun sun
 */
@Service
public class Oauth2ServiceImpl implements IOauth2Service {
	private static final Logger logger = LoggerFactory.getLogger(Oauth2ServiceImpl.class);
	
	@Autowired
	private WechatPublicConfigMapper wechatPublicConfigMapper;

	@Autowired
	private WechatPublicUserMapper wechatPublicUserMapper;

	@Autowired
	private WechatPublicStoreMapper wechatPublicStoreMapper;

	@Autowired
	private WechatUrlMapper wechatUrlMapper;

    @Autowired
	private BaseWxService wxMpService;

	@Autowired
	private ApolloWeChatConfig apolloWeChatConfig;
    
	/**
	 * 获取授权url
	 * 
	 * @param appId
	 * @return
	 */
	@Override
	public ResponseModel authorize(String appId, String redirectUrl) {
		logger.info("进入 WeiXinOAuthService.authorize()");

		if(!StringUtils.isEmpty(redirectUrl)) {
			try {
				String id = "";
				if(StringUtils.isEmpty(appId)) {
					appId = wxMpService.getWxMpConfigStorage().getAppId();
				}
				//由于valid后重定向的页面会不一样，所以先存到wechat_url表，查看此redirectUrl在表里是否已存在，不存在就新建，已存在就直接查出id
				logger.info("执行 wechatUrlMapper.getIdByRedirectUrl() 开始");
				id = wechatUrlMapper.getIdByRedirectUrl(redirectUrl);
				logger.info("执行 wechatUrlMapper.getIdByRedirectUrl() 结束");
				
				if(StringUtils.isEmpty(id)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					id = sdf.format(new Date()) + RandomUtils.getRandom(4);

					logger.info("执行 wechatUrlMapper.create() 开始");
					wechatUrlMapper.create(new WechatUrl(id, redirectUrl));
					logger.info("执行 wechatUrlMapper.create() 结束");
				}
				
				//校验微信公众号appid是否存在
				logger.info("执行 wechatPublicConfigMapper.getAppIdCount() 开始");
				int appIdCount = wechatPublicConfigMapper.getAppIdCount(appId);
				logger.info("执行 wechatPublicConfigMapper.getAppIdCount() 结束");
				
				if(appIdCount <= 0) {
					logger.info("退出 Oauth2ServiceImpl.authorize()");
					return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "微信公众号信息为空！");
				}
				
				String state = appId + "AA" + id;
				//String redirectUri = PropertiesUtil.getContextProperty("redirectUri");
				String redirectUri = apolloWeChatConfig.getRedirectUri();
				String authUrl = wxMpService.oauth2buildAuthorizationUrl(redirectUri, WxConsts.OAUTH2_SCOPE_BASE, state);	
				
				logger.info("退出 Oauth2ServiceImpl.authorize()");
				return new ResponseModel(ResponseCodeEnum.REDIRECT, "重定向到验证链接", authUrl);
			} catch (Exception e) {
				logger.info("退出 Oauth2ServiceImpl.authorize()---"+e.getMessage());
				return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "获取微信用户点击链接的地址有误---");
			}
		}else {
			logger.info("退出 Oauth2ServiceImpl.authorize()");
			return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "返回的链接地址要有两个");
		}
	}

	/**
	 * 验证微信号是否绑定（微信验证回调）
	 * 
	 * @param code, state
	 * @return
	 */
	@Override
	public ResponseModel valid(String code, String state) {
		logger.info("进入 WeiXinOAuthService.valid()");
		try {
			if(StringUtils.isEmpty(state) || (state.split("AA") == null)) {
				logger.info("退出 WeiXinOAuthService.valid()");
				return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "appid为空");
			}
			String[] states = state.split("AA");
			String appId = states[0];
			String id = states[1];
			logger.info("appId==="+appId+"==========id==="+id);
			
			//获取openId
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			String openId = wxMpOAuth2AccessToken.getOpenId();
			logger.info("wxMpOAuth2AccessToken======"+wxMpOAuth2AccessToken+"======openId==="+openId);

			logger.info("执行 dictionaryMapper.getInstructionById() 开始");
			String redirectUrl = wechatUrlMapper.getRedirectUrlById(id);
			logger.info("执行 dictionaryMapper.getInstructionById() 结束==="+redirectUrl);

			//校验用户是否已绑定公众号
			logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 开始");
			String userId = wechatPublicUserMapper.getUserIdByOpenIdAndAppId(new WechatPublicUserDto(openId, appId));
			logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 结束");
			
			String[] redirectUrls = redirectUrl.split(";");
			if(StringUtils.isEmpty(userId)) {
				String unBindUrl = redirectUrls[0]+"&openId="+openId+"&appId="+appId+"&userId="+userId;
				logger.info("退出 WeiXinOAuthService.valid()-----unBindUrl==="+unBindUrl);
				return new ResponseModel(ResponseCodeEnum.SUCCESS, "未绑定", unBindUrl);
				
			}else {
				logger.info("执行 userMapper.getPhoneByUserId() 开始");
				String phone = wechatUrlMapper.getPhoneByUserId(userId);
				logger.info("执行 userMapper.getPhoneByUserId() 结束");
				
				String bindUrl = redirectUrls[1]+"&phone="+phone+"&openId="+openId+"&userId="+userId;
				logger.info("退出 WeiXinOAuthService.valid()-----bindUrl==="+bindUrl);
				return new ResponseModel(ResponseCodeEnum.SUCCESS, "已经绑定", bindUrl);
			}
		} catch (Exception e) {
			logger.info("退出 Oauth2ServiceImpl.valid()---"+e.getMessage());
			return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "验证微信号绑定有误");
		}
	}

	/**
	 * 绑定旺管家账号
	 * 
	 * @param wechatPublicUserDto
	 * @return
	 */
	@Override
	public ResponseModel bind(WechatPublicUserDto wechatPublicUserDto) {
		logger.info("进入 WeiXinOAuthService.bind()!!!");
		if(wechatPublicUserDto == null 
				|| StringUtils.isEmpty(wechatPublicUserDto.getOpenId()) 
				|| StringUtils.isEmpty(wechatPublicUserDto.getAppId())
				|| StringUtils.isEmpty(wechatPublicUserDto.getPhone())) {
			logger.info("退出 WeiXinOAuthService.bind()");
			return new ResponseModel(ResponseCodeEnum.PARAMS_ERROR, "参数错误");
		}
		
		//校验用户是否已绑定公众号
		logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 开始");
		String checkUser = wechatPublicUserMapper.getUserIdByOpenIdAndAppId(new WechatPublicUserDto(wechatPublicUserDto.getOpenId(), wechatPublicUserDto.getAppId()));
		logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 结束");
		
		if(!StringUtils.isEmpty(checkUser)) {
			logger.info("退出 WeiXinOAuthService.bind()");
			return new ResponseModel(ResponseCodeEnum.HAS_EXIST, "该app用户已与微信号绑定");
		}

		logger.error("执行 userMapper.getUserIdByPhone() 开始" +wechatPublicUserDto.getPhone());
		String userId = wechatUrlMapper.getUserIdByPhone(wechatPublicUserDto.getPhone());
		logger.info("执行 userMapper.getUserIdByPhone() 结束");
		
		if(StringUtils.isEmpty(userId)) {
			logger.error("退出 WeiXinOAuthService.bind()" +userId);
			return new ResponseModel(ResponseCodeEnum.DATA_NOT_FOUND, "用户Id不能为空!");
		}
		
		String id = UUID.randomUUID().toString();
		wechatPublicUserDto.setId(id);
		wechatPublicUserDto.setUserId(userId);
		
		logger.info("执行 wechatPublicUserMapper.create() 开始");
		wechatPublicUserMapper.create(wechatPublicUserDto);
		logger.info("执行 wechatPublicUserMapper.create() 结束");
		
		logger.info("退出 WeiXinOAuthService.bind()");
		return new ResponseModel(ResponseCodeEnum.SUCCESS, "success", id);
	}

	@Override
	public ResponseModel bindProvider(WechatPublicUserDto wechatPublicUserDto) {
		logger.info("进入 WeiXinOAuthService.bindProvider()!!!");
		if(wechatPublicUserDto == null
				|| StringUtils.isEmpty(wechatPublicUserDto.getOpenId())
				|| StringUtils.isEmpty(wechatPublicUserDto.getAppId())
				|| StringUtils.isEmpty(wechatPublicUserDto.getUserId())) {
			logger.info("退出 WeiXinOAuthService.bindProvider()");
			return new ResponseModel(ResponseCodeEnum.PARAMS_ERROR, "参数错误");
		}

		//校验用户是否已绑定公众号
		logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 开始");
		String checkUser = wechatPublicUserMapper.getUserIdByOpenIdAndAppId(new WechatPublicUserDto(wechatPublicUserDto.getOpenId(), wechatPublicUserDto.getAppId()));
		logger.info("执行 wechatPublicUserMapper.getUserIdCountByOpenId() 结束");

		if(!StringUtils.isEmpty(checkUser)) {
			logger.info("退出 WeiXinOAuthService.bindProvider()");
			return new ResponseModel(ResponseCodeEnum.HAS_EXIST, "该app用户已与微信号绑定");
		}

		String id = UUID.randomUUID().toString();
		wechatPublicUserDto.setId(id);
		String[] provideIdArray = wechatPublicUserDto.getUserId().split("_");
		if(provideIdArray.length < 2) {
			return new ResponseModel(ResponseCodeEnum.PARAMS_ERROR, "供应商id错误");
		}
		wechatPublicUserDto.setUserId(provideIdArray[1]);

		logger.info("执行 wechatPublicUserMapper.create() 开始");
		wechatPublicUserMapper.create(wechatPublicUserDto);
		logger.info("执行 wechatPublicUserMapper.create() 结束");

		logger.info("退出 WeiXinOAuthService.bindProvider()");
		return new ResponseModel(ResponseCodeEnum.SUCCESS, "success", id);
	}

	@Override
	public ResponseModel bindStore(WxBindStoreDto bindStoreDto) {
		logger.info("进入 WeiXinOAuthService.bindStore()!!!");
		if(Objects.isNull(bindStoreDto)
				|| StringUtils.isEmpty(bindStoreDto.getOpenId())
				|| StringUtils.isEmpty(bindStoreDto.getAppId())
				|| StringUtils.isEmpty(bindStoreDto.getStoreId())) {
			logger.info("退出 WeiXinOAuthService.bindStore()");
			return new ResponseModel(ResponseCodeEnum.PARAMS_ERROR, "参数错误");
		}

		//校验用户是否已绑定该店铺
		logger.info("执行 wechatPublicStoreMapper.getStoreIdByOpenIdAndAppId() 开始");
		List<String> stores = wechatPublicStoreMapper.getStoreIdByOpenIdAndAppId(bindStoreDto);
		logger.info("执行 wechatPublicStoreMapper.getStoreIdByOpenIdAndAppId() 结束");

		for (String bindedStoreId : stores) {
			if(bindedStoreId.equalsIgnoreCase(bindStoreDto.getStoreId())) {
				logger.info("退出 WeiXinOAuthService.bindStore()");
				return new ResponseModel(ResponseCodeEnum.HAS_EXIST, "该微信号已绑定该店铺");
			}
		}

		logger.info("执行 wechatPublicStoreMapper.create() 开始");
		wechatPublicStoreMapper.create(bindStoreDto);
		logger.info("执行 wechatPublicStoreMapper.create() 结束");

		logger.info("退出 WeiXinOAuthService.bindProvider()");
		return new ResponseModel(ResponseCodeEnum.SUCCESS, "success");
	}
}
