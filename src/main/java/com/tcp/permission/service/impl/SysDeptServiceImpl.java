package com.tcp.permission.service.impl;

import com.google.common.base.Preconditions;
import com.tcp.permission.common.ApplicationContextHelper;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysDeptMapper;
import com.tcp.permission.dao.SysUserMapper;
import com.tcp.permission.entity.SysDept;
import com.tcp.permission.exception.ParamException;
import com.tcp.permission.param.DeptParam;
import com.tcp.permission.service.SysDeptService;
import com.tcp.permission.util.BeanValidator;
import com.tcp.permission.util.IpUtil;
import com.tcp.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * @ClassName SysDeptServiceImpl
 * @Description: SysDeptService逻辑处理层
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void saveSysDept(DeptParam deptParam) {
        //1. 校验参数是否满足要求
        BeanValidator.check(deptParam);
        //2. 校验添加的部门是否已经存在
        if (checkExist(deptParam.getId(), deptParam.getName(), deptParam.getParentId())) {
            throw new ParamException("同一层级下部门不成不能相同!");
        }
        //通过lombok @Builder 注解静态方法创建 SysDept
        SysDept dept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId())
                .remark(deptParam.getRemark()).seq(deptParam.getSeq()).build();
        /**
         * 拼接level等级
         * 1.根据parentId查询出当前要添加的部门的父部门，获取父部门的层级level
         * 2.{@link com.tcp.permission.util.LevelUtil#calculateLevel(String, int)} 计算当前要添加部门的层级level
         */
        dept.setLevel(LevelUtil.calculateLevel(getLevel(dept.getParentId()), dept.getParentId()));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        dept.setOperatorTime(new Date());
        sysDeptMapper.insertSelective(dept);
    }

    /**
     * 根据 parentId 获取父部门的层级
     *
     * @param parentId
     * @return
     */
    private String getLevel(Integer parentId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(parentId == null ? 0 : parentId);
        if (sysDept == null) {
            return null;
        } else {
            return sysDept.getLevel();
        }
    }

    @Override
    public void updateSysDept(DeptParam deptParam) {
        //1. 校验参数是否满足要求
        BeanValidator.check(deptParam);
        SysDept beforeSysDept = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        //校验当前需要更新的部门是否存在
        Preconditions.checkNotNull(beforeSysDept, "待更新部门不存在");
        //2. 校验要更新的部门名称是否已经存在
        if (checkExist(deptParam.getId(), deptParam.getName(), deptParam.getParentId())) {
            throw new ParamException("同一层级下部门不成不能相同!");
        }
        SysDept afterSysDept = SysDept.builder().id(deptParam.getId()).seq(deptParam.getSeq())
                .remark(deptParam.getRemark()).parentId(deptParam.getParentId())
                .name(deptParam.getName()).build();
        //TODO
        afterSysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        //TODO
        afterSysDept.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        afterSysDept.setOperatorTime(new Date());
        //计算当前部门的层级
        afterSysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        SysDeptServiceImpl sysDeptServiceImpl = ApplicationContextHelper.popBean(SysDeptServiceImpl.class);
        sysDeptServiceImpl.updateWithChildren(beforeSysDept, afterSysDept);

    }

    @Override
    public void deleteDeptByDeptId(Integer deptId) {
        // 1.根据 deptId 查询子部门
        int deptCount = sysDeptMapper.selectCountByDeptId(deptId);
        if (deptCount > 0) {
            throw new ParamException("当前部门存在子部门,请先删除子部门！");
        }
        // 2. 查询当前部门下是否有用户
        int userCount = sysUserMapper.selectCountByDeptId(deptId);
        if (userCount > 0) {
            throw new ParamException("当前部门下存在用户,不能删除");
        }
        // 删除deptId对应的部门
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }


    public void updateWithChildren(SysDept beforeSysDept, SysDept afterSysDept) {
        //修改完当前部门层级后，重新设计当前原有子部门的层级
        // 1.查询出当前部门下的原有子部门列表
        // 2.重新设置子部门的层级level
        List<SysDept> sysDeptList = sysDeptMapper.getAllDeptByLevel(LevelUtil.calculateLevel(beforeSysDept.getLevel(), beforeSysDept.getId()));
        sysDeptMapper.updateByPrimaryKeySelective(afterSysDept);
        if (!beforeSysDept.getLevel().equals(afterSysDept.getLevel())) {
            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                for (SysDept dept : sysDeptList) {
                    dept.setLevel(LevelUtil.calculateLevel(afterSysDept.getLevel(), dept.getParentId()));
                }
                sysDeptMapper.batchUpdateSysDeptList(sysDeptList);
            }
        }
    }

    /**
     * 校验同一层级下部门是否相同
     *
     * @param id
     * @param name
     * @param parentId
     * @return
     */
    private boolean checkExist(Integer id, String name, Integer parentId) {
        return sysDeptMapper.countByParentIdAndName(parentId == null ? 0 : parentId, name, id) > 0;
    }

}
