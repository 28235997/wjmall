package com.zb.wjmall.config;

import com.zb.wjmall.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/error");
        super.addInterceptors(registry);
    }

}
