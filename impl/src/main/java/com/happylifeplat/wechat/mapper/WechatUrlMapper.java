package com.happylifeplat.wechat.mapper;

import com.happylifeplat.wechat.domain.WechatUrl;
import org.apache.ibatis.annotations.Param;

public interface WechatUrlMapper {

	void create(WechatUrl wechatUrl);

	String getIdByRedirectUrl(String redirectUrl);
	
	String getRedirectUrlById(String dictionaryId);

	String getPhoneByUserId(@Param("userId") String userId);

	String getUserIdByPhone(@Param("phone") String phone);

}
