package com.tcp.permission.service;

/**
 * @ClassName SysRoleAclService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/26 0026
 * @Version V1.0
 **/
public interface SysRoleAclService {
    /**
     * 添加角色权限信息
     *  @param roleId
     * @param aclIds
     */
    void changeRoleAcl(Integer roleId, String aclIds);
}
