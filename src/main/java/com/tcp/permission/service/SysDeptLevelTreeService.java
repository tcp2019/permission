package com.tcp.permission.service;

import com.tcp.permission.dto.DeptLevelDto;

import java.util.List;

/**
 * @ClassName SysDeptLevelTreeService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
public interface SysDeptLevelTreeService {
    /**
     * 查询出每一个层级的部门
     *
     * @return
     */
    List<DeptLevelDto> getDeptLevelTree();
}
