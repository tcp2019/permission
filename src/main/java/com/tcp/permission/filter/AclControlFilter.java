package com.tcp.permission.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.tcp.permission.common.ApplicationContextHelper;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.service.SysCoreService;
import com.tcp.permission.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName AclControlFilter
 * @Description: TODO
 * @Author TCP
 * @Date 2020/6/2 0002
 * @Version V1.0
 **/
@Slf4j
public class AclControlFilter implements Filter {
    private final static Set<String> excludeUrlSet = Sets.newHashSet();
    private static String noAuthPage = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // url 白名单
        String excludeUrls = filterConfig.getInitParameter("excludeUrls");
        List<String> excludeUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(excludeUrls);
        excludeUrlSet.addAll(excludeUrlList);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (excludeUrlSet.contains(servletPath)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 校验是否有权限访问该url
        // 查询该用户可以访问的所有的url ，如果包括该url 则可以访问，否则不可以访问，跳转到无权限页面
        SysUser currentUser = RequestHolder.getCurrentUser();
        if (currentUser == null) {
            //未登录，去无权限访问页面
            log.info("someone no login,no auth:{},params:{}", servletPath, JSONUtils.toJSONString(parameterMap));
            response.sendRedirect(noAuthPage);
        }
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUserAcl(request, response)) {
            log.info("{} visit {},but no auth,params:{}", currentUser.getUsername(), servletPath, JSONUtils.toJSONString(parameterMap));
            response.sendRedirect(noAuthPage);
        } else {
            filterChain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
