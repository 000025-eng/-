package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {




//            //1：获取请求路径（）
//            String requestURI = request.getRequestURI(); // /employee/login
//            //2：判断是否登录请求如果这个路径包含/login,说明是登录操作，放行
//            if(requestURI.contains("/login") ){
//                log.info("登录请求，放行");
//
//                return true;
//            }
            //获取请求头中的token
            String token = request.getHeader("token");
            //判断这个令牌是否存在，如果不存在，说明用户没有登陆，返回错误信息
            if(token ==null|| token.isEmpty()){
                log.info("令牌为空，响应401");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return  false;
            }
            //如果token存在，检验令牌，如果校验失败，返回错误信息（响应401状态码）
            try {
                JwtUtils.parseJwt(token);
            }catch (Exception e){
                log.info("令牌非法，响应401");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            //校验通过，放行
            log.info("登录请求，放行");
            return true;

    }
}
