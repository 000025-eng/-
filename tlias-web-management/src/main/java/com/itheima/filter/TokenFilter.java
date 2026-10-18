package com.itheima.filter;


import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        //1：获取请求路径（）
        String requestURI = request.getRequestURI(); // /employee/login
        //2：判断是否登录请求如果这个路径包含/login,说明是登录操作，放行
        if(requestURI.contains("/login") ){
            log.info("登录请求，放行");
            filterChain.doFilter(request, response);
            return;
        }
        //获取请求头中的token
        String token = request.getHeader("token");
        //判断这个令牌是否存在，如果不存在，说明用户没有登陆，返回错误信息
        if(token ==null|| token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //如果token存在，检验令牌，如果校验失败，返回错误信息（响应401状态码）
        try {
            Claims claims = JwtUtils.parseJwt(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);//存入


            log.info("当前登录员工：{},将其存入Threadlocal" ,empId);
        }catch (Exception e){
            log.info("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //校验通过，放行
        log.info("登录请求，放行");
        filterChain.doFilter(request, response);

        //7.删除ThreadLocal中的数据
        CurrentHolder.remove();
    }
}
