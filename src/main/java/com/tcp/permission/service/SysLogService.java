package com.tcp.permission.service;

import com.tcp.permission.entity.*;
import com.tcp.permission.param.SearchLogParam;

import java.util.List;

/**
 * @ClassName SysLogService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/6/5 0005
 * @Version V1.0
 **/
public interface SysLogService {
    /**
     * 对部门新增、修改和删除时记录操作日志
     *
     * @param before
     * @param after
     */
    void saveDeptLog(SysDept before, SysDept after);

    /**
     * 对用户新增和修改和删除记录操作日志
     *
     * @param before
     * @param after
     */
    void saveUserLog(SysUser before, SysUser after);

    /**
     * 对角色新增、修改和删除时记录操作日志
     *
     * @param before
     * @param after
     */
    void saveRoleLog(SysRole before, SysRole after);

    /**
     * 为用户添加、修改、删除角色时记录操作日志
     *
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleUserLog(Integer roleId, List<Integer> before, List<Integer> after);

    /**
     * 为角色添加、修改、删除权限时记录操作日志
     *
     * @param roleId
     * @param before
     * @param after
     */
    void saveRoleAclLog(Integer roleId, List<Integer> before, List<Integer> after);

    /**
     * 添加、修改和删除权限模块时记录操作日志
     *
     * @param before
     * @param after
     */
    void saveAclModuleLog(SysAclModule before, SysAclModule after);

    /**
     * 添加、修改和删除权限点时记录操作日志
     *
     * @param before
     * @param after
     */
    void saveAclLog(SysAcl before, SysAcl after);

    /**
     *  根据条件查询日志操作轨迹
     * @param pageNo
     * @param pageSize
     * @param searchLogParam
     * @return
     */
    List<SysLog> getSysLogList(Integer pageNo, Integer pageSize, SearchLogParam searchLogParam);

    /**
     *  查询日志条数
     * @param searchLogParam
     * @return
     */
    Integer getCount(SearchLogParam searchLogParam);
}
