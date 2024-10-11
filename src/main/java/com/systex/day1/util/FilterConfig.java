package com.systex.day1.util;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systex.day1.service.UserService;

@Configuration
public class FilterConfig {

    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(UserService userService) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenticationFilter filter = new AuthenticationFilter();
        
        // 使用 Spring 注入的 UserService
        filter.setUserService(userService);

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/lottery/*"); // 添加攔截URL

        return registrationBean;
    }
}
