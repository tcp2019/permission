package com.tcp.permission.service;

import com.tcp.permission.entity.SysAcl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName SysCoreService
 * @Description: 处理角色和用户与权限关系的接口
 * @Author TCP
 * @Date 2020/5/25 0025
 * @Version V1.0
 **/
public interface SysCoreService {
    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getCurrentRoleAclList(Integer roleId);

    List<SysAcl> getUserAclListByUserId(Integer userId);

    boolean hasUserAcl(HttpServletRequest request);
}
