package com.tcp.permission.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.collect.Lists;
import com.tcp.permission.common.JsonMapper;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.constants.CacheConstants;
import com.tcp.permission.dao.SysAclMapper;
import com.tcp.permission.dao.SysRoleAclMapper;
import com.tcp.permission.dao.SysRoleUserMapper;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.service.SysCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName SysCoreServiceImpl
 * @Description: 核心逻辑处理类
 * @Author TCP
 * @Date 2020/5/25 0025
 * @Version V1.0
 **/
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<SysAcl> getCurrentUserAclList() {
        List<Integer> sysRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(RequestHolder.getCurrentUser().getId());
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> sysAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(sysRoleIdList);
        if (CollectionUtils.isEmpty(sysAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getAclListByAclId(sysAclIdList);
    }

    @Override
    public List<SysAcl> getCurrentRoleAclList(Integer roleId) {
        List<Integer> sysAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(sysAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getAclListByAclId(sysAclIdList);
    }

    @Override
    public List<SysAcl> getUserAclListByUserId(Integer userId) {
        List<Integer> sysRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(sysRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> sysAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(sysRoleIdList);
        if (CollectionUtils.isEmpty(sysAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getAclListByAclId(sysAclIdList);
    }

    @Override
    public boolean hasUserAcl(HttpServletRequest request) {
        String url = request.getServletPath();
        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        // 根据当前 url 查询对应的权限点
        List<SysAcl> sysAclList = sysAclMapper.getSysAclByUrl(url);
        boolean hasUserAcl = false;
        if (CollectionUtils.isEmpty(sysAclList)) {
            return true;
        }
        for (SysAcl sysAcl : sysAclList) {
            if (sysAcl == null || sysAcl.getStatus() != 1) {
                hasUserAcl = true;
                continue;
            }
            if (userAclIdSet.contains(sysAcl.getId())) {
                hasUserAcl = true;
                return hasUserAcl;

            }
        }
        return hasUserAcl;
    }

    private List<SysAcl> getCurrentUserAclListFromCache() {
        List<SysAcl> currentUserAclList = Lists.newArrayList();
        String key = CacheConstants.USER_ACLS.name().concat("_").concat(RequestHolder.getCurrentUser().getId().toString());
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isBlank(value)) {
            currentUserAclList = getCurrentUserAclList();

            stringRedisTemplate.boundValueOps(key).set(JsonMapper.obj2String(currentUserAclList), 600000, TimeUnit.MILLISECONDS);
        } else {
            currentUserAclList = JsonMapper.string2Obj(value, new TypeReference<List<SysAcl>>() {
            });
        }
        return currentUserAclList;
    }
}
