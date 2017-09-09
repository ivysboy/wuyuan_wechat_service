package com.happylifeplat.wechat.mapper;

import com.happylifeplat.wechat.model.WxBindStoreDto;

import java.util.List;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
public interface WechatPublicStoreMapper {


    /**
     * 新建 微信用户和店铺绑定关系
     *
     * @param bindStoreDto
     */
    void create(WxBindStoreDto bindStoreDto);

    /**
     * 校验用户是否已绑定公众号
     *
     * @param bindStoreDto
     * @return
     */
    List<String> getStoreIdByOpenIdAndAppId(WxBindStoreDto bindStoreDto);

    /**
     * 根据openid和appid删除所有用户绑定
     * @param bindStoreDto
     * @return
     */
    int disBindStoresByOpenIdAndAppId(WxBindStoreDto bindStoreDto);
}
