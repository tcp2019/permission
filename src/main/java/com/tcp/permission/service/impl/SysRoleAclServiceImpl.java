package com.tcp.permission.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.constants.CacheConstants;
import com.tcp.permission.dao.SysRoleAclMapper;
import com.tcp.permission.entity.SysRoleAcl;
import com.tcp.permission.service.SysLogService;
import com.tcp.permission.service.SysRoleAclService;
import com.tcp.permission.util.IpUtil;
import com.tcp.permission.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Cache;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleAclServiceImpl
 * @Description: 角色权限逻辑处理类
 * @Author TCP
 * @Date 2020/5/26 0026
 * @Version V1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleAclServiceImpl implements SysRoleAclService {
    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void changeRoleAcl(Integer roleId, String aclIds) {
        //将 aclIds 转化为List<Integer>
        List<Integer> aclIdList = StringUtil.handle(aclIds);
        //根据 roleId查询出对应的aclId ，和aclIdList对比是否一致，如果一致，则不需要修改
        List<Integer> originalAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEqualCollection(originalAclIdList, aclIdList)) {
            return;
        }
        insertSysRoleAclWithRoleIdAndAclIdList(roleId, aclIdList);
        sysLogService.saveRoleAclLog(roleId, originalAclIdList, aclIdList);
        String key = CacheConstants.USER_ACLS.name().concat("_").concat(RequestHolder.getCurrentUser().getId().toString());
        stringRedisTemplate.delete(key);
    }

    public void insertSysRoleAclWithRoleIdAndAclIdList(Integer roleId, List<Integer> aclIdList) {
        // 删除 原有roleId相关的权限信息
        sysRoleAclMapper.deleteByRoleId(roleId);
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().aclId(aclId).roleId(roleId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest())).operatorTime(new Date()).build();
            sysRoleAclMapper.insertSelective(sysRoleAcl);
        }
    }
}
