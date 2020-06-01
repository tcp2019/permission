package com.tcp.permission.service;

import com.tcp.permission.dto.SysAclDto;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.param.SysRoleParam;

import java.util.List;

/**
 * @ClassName SysRoleService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/22 0022
 * @Version V1.0
 **/
public interface SysRoleService {
    /**
     * 添加角色信息
     *
     * @param sysRoleParam
     */
    void saveSysRole(SysRoleParam sysRoleParam);

    /**
     * 添加角色信息
     *
     * @param sysRoleParam
     */
    void updateSysRole(SysRoleParam sysRoleParam);

    /**
     * 查询roleList列表
     *
     * @return
     */
    List<SysRole> getSysRoleList();

    List<SysRole> getRoleListByAclId(Integer aclId);
}
