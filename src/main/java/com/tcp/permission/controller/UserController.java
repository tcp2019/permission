package com.tcp.permission.controller;

import com.tcp.permission.entity.SysUser;
import com.tcp.permission.service.SysUserService;
import com.tcp.permission.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @ClassName UserController
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login.page")
    public String login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //登录页带的重定向地址
        String ret = request.getParameter("ret");
        SysUser sysUser = sysUserService.findSysUserByKeyword(username);
        String errorMsg = "";
        if (StringUtils.isBlank(username)) {
            errorMsg = "登录名不能为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "登录密码不能为空";
        } else if (sysUser == null || !sysUser.getPassword().equals(MD5Util.md5Encrypt32Upper(password))) {
            errorMsg = "登录名或者密码错误";
        } else {
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isBlank(ret)) {
                return "views/admin";
            } else {
                String decodeRedirectUrl = URLDecoder.decode(ret, "UTF-8");
                return "redirect:/" + decodeRedirectUrl;
            }
        }

        //登录失败，重新回到login页面
        request.setAttribute("errorMsg", errorMsg);
        request.setAttribute("username", username);
        return "login.jsp";
    }
}
