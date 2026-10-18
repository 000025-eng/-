package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")//拦截请求的路径


//一般来说init和destroy这两个方法是不用实现的，官方已经设置默认属性了
public class Demofilter implements Filter {
    //初始化，web服务器启动的时候执行，只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init初始化方法");
    }

    //拦截到请求后执行，执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到了请求,放行前");
        //放行操作
        filterChain.doFilter(servletRequest, servletResponse);
        //放行后
        log.info("放行后的");
    }
    //销毁方法，web服务器关闭的时候执行，只执行一次
    @Override
    public void destroy() {
       log.info("destroy销毁资源，释放资源");
    }
}
