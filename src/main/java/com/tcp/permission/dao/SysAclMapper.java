package com.tcp.permission.dao;

import com.tcp.permission.entity.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    /**
     * 获取权限点列表
     *
     * @param aclModuleId
     * @param pageSize
     * @param pageNo
     * @return
     */
    List<SysAcl> aclList(@Param("aclModuleId") Integer aclModuleId, @Param("pageSize") Integer pageSize, @Param("pageNo") Integer pageNo);

    /**
     * 获取权限点总条数
     *
     * @param aclModuleId
     * @return
     */
    int getCount(Integer aclModuleId);

    int countByaclModuleIdAndName(@Param("id") int id, @Param("name") String name, @Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> getAllList();

    List<SysAcl> getAclListByAclId(@Param("sysAclIdList") List<Integer> sysAclIdList);
}