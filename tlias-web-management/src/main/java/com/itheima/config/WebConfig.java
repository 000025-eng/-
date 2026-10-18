package com.itheima.config;

import com.itheima.interceptor.DemoInterceptor;
import com.itheima.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration//配置类
public class WebConfig implements WebMvcConfigurer {

    // @Autowired
      //  private DemoInterceptor demoInterceptor;

        //测试demointercepter
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(tokenInterceptor)
//                    .addPathPatterns("/**")//拦截所有请求
//                    .excludePathPatterns("/login");//放行请求
//        }

}
