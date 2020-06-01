package com.tcp.permission.service;

import com.tcp.permission.param.DeptParam;

/**
 * @ClassName SysDeptService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
public interface SysDeptService {
    /**
     * 添加部门
     *
     * @param deptParam
     */
    void saveSysDept(DeptParam deptParam);

    void updateSysDept(DeptParam deptParam);

    /**
     * 根据部门id删除部门
     *
     * @param deptId
     */
    void deleteDeptByDeptId(Integer deptId);
}
