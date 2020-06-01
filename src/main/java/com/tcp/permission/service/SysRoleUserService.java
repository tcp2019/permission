package com.tcp.permission.service;

import com.tcp.permission.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleUserService
 * @Description: 角色和用户接口
 * @Author TCP
 * @Date 2020/6/1 0001
 * @Version V1.0
 **/
public interface SysRoleUserService {
    /**
     * 根据roleId 查询出所有已分配该角色得到用户和未分配该角色的用户
     *
     * @param roleId
     * @return
     */
    Map<String, List<SysUser>> users(Integer roleId);

    /**
     * 为userIds 包含的用户分配角色权限
     *
     * @param roleId
     * @param userIds
     */
    void changeUsers(Integer roleId, String userIds);
}
