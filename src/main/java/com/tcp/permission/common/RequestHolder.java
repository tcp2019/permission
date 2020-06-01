package com.tcp.permission.common;

import com.tcp.permission.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RequestHolder
 * @Description: 存储当前 SysUser 信息的 ThreadLocal
 * @Author TCP
 * @Date 2020/5/11 0011
 * @Version V1.0
 **/
public class RequestHolder {
    public static final ThreadLocal<SysUser> sysUserThreadLocal = new ThreadLocal<>();
    public static final ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        sysUserThreadLocal.set(sysUser);
    }

    public static SysUser getCurrentUser() {
        return sysUserThreadLocal.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return httpServletRequestThreadLocal.get();
    }

    public static void add(HttpServletRequest request) {
        httpServletRequestThreadLocal.set(request);
    }

    public void remove() {
        sysUserThreadLocal.remove();
        httpServletRequestThreadLocal.remove();
    }
}
