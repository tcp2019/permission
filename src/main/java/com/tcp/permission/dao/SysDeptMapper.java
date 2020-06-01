package com.tcp.permission.dao;

import com.tcp.permission.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getAllDept();

    /**
     * 根据id查询出当前部门的所有子部门
     *
     * @param level
     * @return
     */
    List<SysDept> getAllDeptByLevel(String level);

    int batchUpdateSysDeptList(@Param("sysDeptList") List<SysDept> sysDeptList);

    /**
     * 添加：根据parentId和name查询sysDept是否存在
     * 修改：根据parentId和name查询出除自身外的其他部门
     *
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    int countByParentIdAndName(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    int selectCountByDeptId(@Param("deptId") Integer deptId);
}