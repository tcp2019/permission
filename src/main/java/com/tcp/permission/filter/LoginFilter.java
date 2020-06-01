package com.tcp.permission.filter;

import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.entity.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LoginFilter
 * @Description: 登录拦截器
 * @Author TCP
 * @Date 2020/5/11 0011
 * @Version V1.0
 **/
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //从 session 中获取 sysUser
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser != null) {
            RequestHolder.add(sysUser);
            RequestHolder.add(request);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/login.jsp");
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
