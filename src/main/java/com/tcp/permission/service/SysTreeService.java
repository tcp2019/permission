package com.tcp.permission.service;

import com.tcp.permission.dto.AclModuleLevelDto;
import com.tcp.permission.dto.DeptLevelDto;

import java.util.List;

/**
 * @ClassName SysTreeService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/25 0025
 * @Version V1.0
 **/
public interface SysTreeService {
    List<AclModuleLevelDto> getAclModuleLevelTree();

    List<DeptLevelDto> getDeptLevelTree();

    List<AclModuleLevelDto> roleTree(Integer roleId);

    List<AclModuleLevelDto> userAclTree(Integer userId);
}
