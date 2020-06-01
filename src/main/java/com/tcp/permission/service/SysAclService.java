package com.tcp.permission.service;

import com.tcp.permission.beans.PageResult;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.param.SysAclParam;

/**
 * @ClassName SysAclService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/21 0021
 * @Version V1.0
 **/
public interface SysAclService {
    /**
     * 根据权限模块id获取该权限模块下的所有权限点
     *
     * @param aclModuleId
     * @param pageSize
     * @param pageNo
     * @return
     */
    PageResult<SysAcl> aclList(Integer aclModuleId, Integer pageSize, Integer pageNo);

    /**
     * 查询某个权限点信息
     *
     * @param aclId
     * @return
     */
    SysAcl getAcl(Integer aclId);

    void saveSysAcl(SysAclParam sysAclParam);

    void updateAcl(SysAclParam sysAclParam);
}
