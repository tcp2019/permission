package com.tcp.permission.service.impl;

import com.google.common.base.Preconditions;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysAclModuleMapper;
import com.tcp.permission.entity.SysAclModule;
import com.tcp.permission.entity.SysDept;
import com.tcp.permission.exception.ParamException;
import com.tcp.permission.param.SysAclModuleParam;
import com.tcp.permission.service.SysAclModuleService;
import com.tcp.permission.util.BeanValidator;
import com.tcp.permission.util.IpUtil;
import com.tcp.permission.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName SysAclModuleServiceImpl
 * @Description: 权限模块逻辑处理类
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Override
    public void saveSysAclModule(SysAclModuleParam sysAclModuleParam) {
        //校验参数格式
        BeanValidator.check(sysAclModuleParam);
        if (checkExist(null, sysAclModuleParam.getName(), sysAclModuleParam.getParentId())) {
            throw new ParamException("同一层级下权限模块不能相同");
        }
        SysAclModule sysAclModule = SysAclModule.builder().name(sysAclModuleParam.getName())
                .parentId(sysAclModuleParam.getParentId()).seq(sysAclModuleParam.getSeq()).remark(sysAclModuleParam.getRemark())
                .level(LevelUtil.calculateLevel(getLevel(sysAclModuleParam.getParentId()), sysAclModuleParam.getParentId())).build();
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperatorTime(new Date());
        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    @Override
    public void updateSysAclModule(SysAclModuleParam sysAclModuleParam) {
        BeanValidator.check(sysAclModuleParam);
        //校验待更新部门是否存在
        SysAclModule beforeSysAclModule = sysAclModuleMapper.selectByPrimaryKey(sysAclModuleParam.getId());
        Preconditions.checkNotNull(beforeSysAclModule, "待更新权限模块不存在!");
        if (checkExist(sysAclModuleParam.getId(), sysAclModuleParam.getName(), sysAclModuleParam.getParentId())) {
            throw new ParamException("同一层级下权限模块不能相同");
        }
        SysAclModule sysAclModule = SysAclModule.builder().id(sysAclModuleParam.getId()).name(sysAclModuleParam.getName())
                .parentId(sysAclModuleParam.getParentId()).seq(sysAclModuleParam.getSeq()).remark(sysAclModuleParam.getRemark())
                .level(LevelUtil.calculateLevel(getLevel(sysAclModuleParam.getParentId()), sysAclModuleParam.getParentId())).build();
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperatorTime(new Date());
        sysAclModuleMapper.updateByPrimaryKeySelective(sysAclModule);
    }

    /**
     * 校验同一层级下权限模块是否相同
     *
     * @param id
     * @param name
     * @param parentId
     * @return
     */
    private boolean checkExist(Integer id, String name, Integer parentId) {
        return sysAclModuleMapper.countByParentIdAndName(parentId == null ? 0 : parentId, name, id) > 0;
    }

    /**
     * 根据 parentId 获取父部门的层级
     *
     * @param parentId
     * @return
     */
    private String getLevel(Integer parentId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(parentId == null ? 0 : parentId);
        if (sysAclModule == null) {
            return null;
        } else {
            return sysAclModule.getLevel();
        }
    }

}
