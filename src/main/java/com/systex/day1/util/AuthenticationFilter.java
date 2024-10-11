package com.systex.day1.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.systex.day1.model.User;
import com.systex.day1.service.UserService;

@Component
public class AuthenticationFilter implements Filter {
	
	@Autowired 
    @Setter
    private UserService userService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // CSP
        res.setHeader("Content-Security-Policy", 
        	    "default-src 'self'; " +
        	    "script-src 'self' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com 'unsafe-inline' 'unsafe-eval'; " +
        	    "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://stackpath.bootstrapcdn.com; " +
        	    "connect-src 'self'; " +
        	    "img-src 'self' data:; " +
        	    "frame-src 'self';");

        String uri = req.getRequestURI();

        if (uri.startsWith(req.getContextPath() + "/lottery")) {
            // 檢查 session 中是否有用戶信息
            Object userSession = req.getSession().getAttribute("user");
            if (userSession != null) {
                // 用戶已登入，進一步檢查有效性
                User user = (User) userSession;
                if (userService.findByUsername(user.getUsername()) != null) {
                    // 用戶有效，繼續處理請求
                    chain.doFilter(request, response);
                    return;
                }
            }
            // 如果用戶未登入或無效，則重定向到登入頁面
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            // 如果不屬於需要白名單的範圍，則直接放行
            chain.doFilter(request, response);
        }
    }

    //清除
    @Override
    public void destroy() {
    }
}
