package com.tcp.permission.dao;

import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getAclIdListByRoleIdList(@Param("sysRoleIdList") List<Integer> sysRoleIdList);

    /**
     *  根据roleId删除对应的权限信息
     *
     * @param roleId
     * @return
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);

    List<Integer> getRoleIdListByAclId(@Param("aclId") Integer aclId);
}