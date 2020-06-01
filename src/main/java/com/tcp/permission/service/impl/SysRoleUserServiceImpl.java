package com.tcp.permission.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysRoleUserMapper;
import com.tcp.permission.dao.SysUserMapper;
import com.tcp.permission.entity.SysRoleUser;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.service.SysRoleUserService;
import com.tcp.permission.util.IpUtil;
import com.tcp.permission.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleUserServiceImpl
 * @Description: 角色和用户的逻辑处理类
 * @Author TCP
 * @Date 2020/6/1 0001
 * @Version V1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Map<String, List<SysUser>> users(Integer roleId) {
        List<SysUser> selectedSysUserList = null;
        // 1. 根据roleId查询出所有的user
        List<Integer> selectedSysUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(selectedSysUserIdList)) {
            selectedSysUserIdList = Lists.newArrayList();
        } else {
            selectedSysUserList = sysUserMapper.getUserListByUserIdList(selectedSysUserIdList);
        }
        // 2. 查询出所有的user，过滤出未分配该角色的用户
        List<SysUser> sysUserList = sysUserMapper.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();
        //过滤出所有的userIdList，便于遍历
        // 3. 将已分配该角色和未分配该角色的user分别存到map中
        for (SysUser sysUser : sysUserList) {
            if (sysUser.getStatus() == 1 && !selectedSysUserIdList.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        Map<String, List<SysUser>> sysUserMap = Maps.newHashMap();
        sysUserMap.put("selected", selectedSysUserList);
        sysUserMap.put("unselected", unselectedUserList);
        return sysUserMap;
    }

    @Override
    public void changeUsers(Integer roleId, String userIds) {
        // 根据 userIds "," 分割字符串
        List<Integer> userIdList = StringUtil.handle(userIds);
        // 查询roleId 所分配的用户
        List<Integer> userIdListByRoleId = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEqualCollection(userIdListByRoleId, userIdList)) {
            return;
        }
        // 删除之前已经分配的角色用户关系
        sysRoleUserMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(userIdList)) {
            SysRoleUser sysRoleUser = SysRoleUser.builder().roleId(roleId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest())).operatorTime(new Date()).build();
            for (Integer userId : userIdList) {
                sysRoleUser.setUserId(userId);
                sysRoleUserMapper.insertSelective(sysRoleUser);
            }
        }
    }
}
