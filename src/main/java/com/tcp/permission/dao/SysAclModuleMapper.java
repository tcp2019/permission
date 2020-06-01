package com.tcp.permission.dao;

import com.tcp.permission.entity.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByParentIdAndName(@Param("parentId") Integer parentId, @Param("name")String name, @Param("id")Integer id);

    List<SysAclModule> getAllAclModule();
}