package com.happylifeplat.wechat;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.happylifeplat.wechat.config.ApolloWeChatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by TiejunSun on 2016-09-08.
 * 微信公众号服务
 */
@EnableResourceServer
@EnableOAuth2Sso
@SpringBootApplication
@EnableJms
@ImportResource({"classpath:spring/applicationContext-dataSource.xml", "classpath:spring/applicationContext.xml"})
@EnableApolloConfig({"application","TEST1.apusic.config"})
public class Application {

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

    @Bean
    @Primary
    public UserInfoTokenServices userInfoTokenServices() {
        UserInfoTokenServices userInfoTokenServices = new UserInfoTokenServices(apolloWeChatConfig.getOauthUserInfoUri(), "");
        return userInfoTokenServices;
    }

}
