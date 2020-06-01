package com.tcp.permission.service;

import com.tcp.permission.param.SysAclModuleParam;

/**
 * @ClassName SysAclModuleService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
public interface SysAclModuleService {
    /**
     * 添加权限模块
     *
     * @param sysAclModuleParam
     */
    void saveSysAclModule(SysAclModuleParam sysAclModuleParam);

    /**
     * 修改权限模块信息
     *
     * @param sysAclModuleParam
     */
    void updateSysAclModule(SysAclModuleParam sysAclModuleParam);
}
