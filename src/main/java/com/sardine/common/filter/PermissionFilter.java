package com.sardine.common.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sardine.bean.Response;
import com.sardine.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class PermissionFilter implements Filter {
    @Autowired
    private LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("get request: [{}]", request.getRequestURI());
        if (request.getRequestURI().endsWith("/api/v0/session") && "POST".equals(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = request.getHeader("token");
        if (loginService.checkTokenValid(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            out.write(objectMapper.writeValueAsString(Response.fail("invalid token")));
            out.flush();
        }
    }

    @Override
    public void destroy() {

    }
}
