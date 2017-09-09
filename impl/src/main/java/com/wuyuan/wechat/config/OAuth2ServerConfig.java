package com.wuyuan.wechat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by vincent on 2017-07-07.
 */
@Configuration
public class OAuth2ServerConfig {

    @Autowired
    private ApolloWeChatConfig apolloWeChatConfig;

    private static final String OAUTH_ENABLE = "false";

    @Configuration
    @EnableResourceServer
    @Order(1)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {

            if(!Boolean.TRUE.toString().equals(OAUTH_ENABLE)) {
                http.authorizeRequests().anyRequest().permitAll();
            } else {
                http
                        .authorizeRequests()
                        .antMatchers("/swagger-ui.html", "/swagger-resources/**",
                                "/v2/api-docs/**", "/validatorUrl", "/alipayCallbackFun/**", "/wxCallbackFun/**",
                                "/valid")
                        .permitAll()
                        .anyRequest()
                        .authenticated();
            }
        }
    }
}
