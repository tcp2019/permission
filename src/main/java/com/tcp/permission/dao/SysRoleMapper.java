package com.tcp.permission.dao;

import com.tcp.permission.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 查询角色名称是否存在
     * 添加：判断名称是否存在
     * 修改：判断非当前id的角色名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    int getCountByIdAndName(@Param("id") Integer id, @Param("name") String name);

    List<SysRole> getSysRoleList();
}