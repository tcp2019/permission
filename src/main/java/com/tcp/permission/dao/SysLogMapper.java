package com.tcp.permission.dao;

import com.tcp.permission.entity.SysLog;
import com.tcp.permission.entity.SysLogWithBLOBs;
import com.tcp.permission.param.SearchLogParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> getSysLogList(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("searchLogParam") SearchLogParam searchLogParam);

    Integer getCount(@Param("searchLogParam")SearchLogParam searchLogParam);
}