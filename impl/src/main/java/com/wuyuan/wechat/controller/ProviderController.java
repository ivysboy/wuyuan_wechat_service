package com.wuyuan.wechat.controller;

import com.happylifeplat.Result;
import com.happylifeplat.commons.utils.UUIDUtils;
import com.happylifeplat.messagecode.impl.WechatCode;
import com.wuyuan.wechat.config.ApolloWeChatConfig;
import com.wuyuan.wechat.mapper.WechatPublicStoreMapper;
import com.wuyuan.wechat.model.WxBindStoreDto;
import com.wuyuan.wechat.model.result.WxProviderStoresDto;
import com.wuyuan.wechat.service.IOauth2Service;
import com.wuyuan.wechat.service.impl.WxStoreServiceImpl;
import com.wuyuan.wechat.util.ResponseCodeEnum;
import com.wuyuan.wechat.util.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private WxStoreServiceImpl storeService;

    @Autowired
    private IOauth2Service oauth2Service;

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    @Autowired
    private WechatPublicStoreMapper storeMapper;

    private static final Logger log = LoggerFactory.getLogger(ProviderController.class);

    @RequestMapping(value = "/getStores", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result getStoresByProviderId(@RequestParam String providerId) {
        if(StringUtils.isEmpty(providerId)) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        if(!UUIDUtils.isUUID(providerId)) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        WxProviderStoresDto result = storeService.getStoresByProviderId(providerId);
        if(Objects.isNull(result)) {
            return Result.error(WechatCode.BIND_PROVIDER_ERROR);
        }

        return Result.success(result);
    }

    @RequestMapping(value = "/bindStoreWithPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result bindStoreWithPara(@RequestBody WxBindStoreDto bindStoreDto) {

        if(Objects.isNull(bindStoreDto)
                || StringUtils.isEmpty(bindStoreDto.getOpenId())
                || StringUtils.isEmpty(bindStoreDto.getStoreId())) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        bindStoreDto.setAppId(apolloWeChatConfig.getAppid());

        String[] storeIds = bindStoreDto.getStoreId().split(",");
        if(storeIds.length < 1) {
            return Result.error(WechatCode.PARAMETER_ERROR);
        }

        // 先删掉该用户所有的绑定关系
        try {
            storeMapper.disBindStoresByOpenIdAndAppId(bindStoreDto);
        } catch (Exception e) {
            log.error("============删除绑定关系失败: " + e.getMessage());
            return Result.error();
        }

        for(String storeId: storeIds) {
            bindStoreDto.setStoreId(storeId);
            ResponseModel responseModel = oauth2Service.bindStore(bindStoreDto);
            if(responseModel.getCode() != ResponseCodeEnum.SUCCESS.getCode()) {
                log.error("===============绑定店铺（id: {}）失败", storeId);
                continue;
            }
        }

        return Result.success();
    }

}
