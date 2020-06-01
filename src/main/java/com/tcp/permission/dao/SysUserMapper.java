package com.tcp.permission.dao;

import com.tcp.permission.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findSysUserByKeyword(String username);

    int checkMailExist(@Param("mail") String mail, @Param("id") Integer userId);

    int checkTelephoneExist(@Param("telephone") String telephone, @Param("id") Integer userId);

    int getCount(Integer deptId);

    List<SysUser> getUserList(@Param("deptId") Integer deptId, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<SysUser> getAll();

    List<SysUser> getUserListByUserIdList(@Param("userIdList") List<Integer> selectedSysUserIdList);

    int selectCountByDeptId(@Param("deptId") Integer deptId);
}