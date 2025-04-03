package com.example.schedulemanageapp.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter("/api/*")  // 모든 API 요청에 대해 필터링
public class LoginFilter implements Filter {

    // 필터 초기화 작업
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // 회원가입 경로는 필터링 제외
        String uri = httpServletRequest.getRequestURI();
        if (uri.contains("/signup")) {
            chain.doFilter(servletRequest, servletResponse);  // 회원가입 경로는 필터링 하지 않음
            return;
        }

        // 세션에서 사용자 정보 확인
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            // 인증된 사용자가 있으면 요청을 계속 진행
            chain.doFilter(servletRequest, servletResponse);
        } else {
            // 인증되지 않은 경우
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
        }
    }

    @Override
    public void destroy() {}
}
