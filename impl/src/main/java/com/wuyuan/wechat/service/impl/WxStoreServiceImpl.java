package com.wuyuan.wechat.service.impl;

import com.happylifeplat.serviceprovider.model.result.ServiceProviderResultDto;
import com.happylifeplat.serviceprovider.service.IServiceProviderService;
import com.happylifeplat.stores.service.IStoresService;
import com.happylifeplat.stores.service.domain.Stores;
import com.wuyuan.wechat.model.result.WxProviderStoresDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
@Component
public class WxStoreServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(WxStoreServiceImpl.class);

    @Autowired(required = false)
    private IServiceProviderService serviceProviderService;

    @Autowired(required = false)
    private IStoresService storesService;

    public WxProviderStoresDto getStoresByProviderId(String providerId) {
        if(StringUtils.isEmpty(providerId)) {
            return null;
        }

        WxProviderStoresDto providerStoresDto = new WxProviderStoresDto();
        providerStoresDto.setId(providerId);

        try {
            ServiceProviderResultDto providerResultDto = serviceProviderService.getById(providerId);
            if(Objects.nonNull(providerResultDto)) {

                providerStoresDto.setProviderName(providerResultDto.getName());
            }

        } catch (Exception e) {
            log.error("====================获取供应商(id: {})信息失败: {}" , providerId, e.getMessage());
        }

        List<Stores> stores = storesService.queryByProviderId(providerId);
        providerStoresDto.setStores(stores);
        return providerStoresDto;
    }
}
