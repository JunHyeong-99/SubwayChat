package com.cloud.SubwayChat.core.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId == null) { // 로그인 안 되어있으면 로그인 부터
            response.sendRedirect("/");
            return false; // 컨트롤러 메소드 실행을 중단
        }
        return true; // 컨트롤러 메소드 실행 계속
    }
}