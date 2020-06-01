package com.tcp.permission.service;

import com.tcp.permission.beans.PageResult;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.param.UserParam;

import java.util.List;

/**
 * @ClassName SysUserService
 * @Description: SysUserService 接口
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
public interface SysUserService {
    /**
     * 添加用户信息
     *
     * @param userParam
     */
    void saveSysUser(UserParam userParam);

    void updateSysUser(UserParam userParam);

    SysUser findSysUserByKeyword(String username);

    PageResult<SysUser> getUserList(Integer deptId, Integer pageNo, Integer pageSize);

    List<SysRole> getRoleListByUserId(Integer userId);

    List<SysUser> getUserListBySysRoleList(List<SysRole> sysRoleList);
}
