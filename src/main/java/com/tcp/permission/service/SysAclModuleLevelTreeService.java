package com.tcp.permission.service;

import com.tcp.permission.dto.AclModuleLevelDto;

import java.util.List;

/**
 * @ClassName SysAclModuleLevelTreeService
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
public interface SysAclModuleLevelTreeService {
    /**
     * 获取权限模块树
     *
     * @return
     */
    List<AclModuleLevelDto> getAclModuleLevelTree();
}
