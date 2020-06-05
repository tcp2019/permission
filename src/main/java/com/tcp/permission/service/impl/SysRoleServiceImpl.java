package com.tcp.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysAclMapper;
import com.tcp.permission.dao.SysAclModuleMapper;
import com.tcp.permission.dao.SysRoleAclMapper;
import com.tcp.permission.dao.SysRoleMapper;
import com.tcp.permission.dto.SysAclDto;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.exception.ParamException;
import com.tcp.permission.param.SysRoleParam;
import com.tcp.permission.service.SysCoreService;
import com.tcp.permission.service.SysLogService;
import com.tcp.permission.service.SysRoleService;
import com.tcp.permission.util.BeanValidator;
import com.tcp.permission.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysRoleServiceImpl
 * @Description: 角色业务逻辑处理
 * @Author TCP
 * @Date 2020/5/22 0022
 * @Version V1.0
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void saveSysRole(SysRoleParam sysRoleParam) {
        //校验参数格式
        BeanValidator.check(sysRoleParam);
        //校验当前角色名称是否已经存在
        if (checkExist(sysRoleParam.getId(), sysRoleParam.getName())) {
            throw new ParamException("当前角色名称已存在");
        }
        SysRole sysRole = SysRole.builder().name(sysRoleParam.getName()).status(sysRoleParam.getStatus())
                .type(sysRoleParam.getType()).remark(sysRoleParam.getRemark()).build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysRole.setOperatorTime(new Date());
        sysRoleMapper.insertSelective(sysRole);
        sysLogService.saveRoleLog(null, sysRole);
    }

    @Override
    public void updateSysRole(SysRoleParam sysRoleParam) {
        //校验参数格式
        BeanValidator.check(sysRoleParam);
        //校验当前角色名称是否已经存在
        if (checkExist(sysRoleParam.getId(), sysRoleParam.getName())) {
            throw new ParamException("当前角色名称已存在");
        }
        SysRole beforeSysRole = sysRoleMapper.selectByPrimaryKey(sysRoleParam.getId());
        SysRole afterSysRole = SysRole.builder().name(sysRoleParam.getName()).status(sysRoleParam.getStatus())
                .type(sysRoleParam.getType()).remark(sysRoleParam.getRemark()).id(sysRoleParam.getId()).build();
        afterSysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        afterSysRole.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        afterSysRole.setOperatorTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(afterSysRole);
        sysLogService.saveRoleLog(beforeSysRole, afterSysRole);
    }

    @Override
    public List<SysRole> getSysRoleList() {
        return sysRoleMapper.getSysRoleList();
    }

    @Override
    public List<SysRole> getRoleListByAclId(Integer aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoleList = Lists.newArrayList();
        for (Integer roleId : roleIdList) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
            sysRoleList.add(sysRole);
        }
        return sysRoleList;
    }


    private boolean checkExist(Integer id, String name) {
        return sysRoleMapper.getCountByIdAndName(id, name) >= 1;
    }


}
