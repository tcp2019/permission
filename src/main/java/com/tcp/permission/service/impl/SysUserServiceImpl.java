package com.tcp.permission.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tcp.permission.beans.PageResult;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysDeptMapper;
import com.tcp.permission.dao.SysRoleMapper;
import com.tcp.permission.dao.SysRoleUserMapper;
import com.tcp.permission.dao.SysUserMapper;
import com.tcp.permission.entity.SysDept;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.exception.ParamException;
import com.tcp.permission.param.UserParam;
import com.tcp.permission.service.SysLogService;
import com.tcp.permission.service.SysRoleUserService;
import com.tcp.permission.service.SysUserService;
import com.tcp.permission.util.BeanValidator;
import com.tcp.permission.util.IpUtil;
import com.tcp.permission.util.MD5Util;
import com.tcp.permission.util.PasswordGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysUserServiceImpl
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void saveSysUser(UserParam userParam) {
        //校验 userParam 参数是否符合要求
        BeanValidator.check(userParam);
        if (checkMailExist(userParam.getMail(), null)) {
            throw new ParamException("当前邮箱号已存在");
        }
        if (checkTelephoneExist(userParam.getTelephone(), null)) {
            throw new ParamException("当前电话号已存在");
        }
        //查询当前添加用户所在部门是否存在
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(userParam.getDeptId());
        if (sysDept == null) {
            throw new ParamException("用户所在部门不存在");
        } else {
            String password = PasswordGeneratorUtil.randomPassword();
            log.info("password==>{}", password);
            SysUser sysUser = SysUser.builder().username(userParam.getUsername()).deptId(userParam.getDeptId())
                    .mail(userParam.getMail()).remark(userParam.getRemark()).password(MD5Util.md5Encrypt32Upper(password))
                    .telephone(userParam.getTelephone()).status(userParam.getStatus()).build();
            sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            sysUser.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
            sysUser.setOperatorTime(new Date());

            //TODO sendPasswordEmail
            sysUserMapper.insertSelective(sysUser);
            sysLogService.saveUserLog(null, sysUser);
        }
    }

    @Override
    public void updateSysUser(UserParam userParam) {
        //校验 userParam 参数是否符合要求
        BeanValidator.check(userParam);
        SysUser beforeSysUser = sysUserMapper.selectByPrimaryKey(userParam.getId());
        //校验当前需要更新的部门是否存在
        Preconditions.checkNotNull(beforeSysUser, "待更新用户不存在");
        if (checkMailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("当前邮箱号已存在");
        }
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("当前电话号已存在");
        }
        //查询当前添加用户所在部门是否存在
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(userParam.getDeptId());
        if (sysDept == null) {
            throw new ParamException("用户所在部门不存在");
        } else {
            SysUser afterSysUser = SysUser.builder().username(userParam.getUsername()).deptId(userParam.getDeptId())
                    .mail(userParam.getMail()).remark(userParam.getRemark()).telephone(userParam.getTelephone())
                    .status(userParam.getStatus()).id(userParam.getId()).build();
            afterSysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            afterSysUser.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
            afterSysUser.setOperatorTime(new Date());

            //TODO sendPasswordEmail
            sysUserMapper.updateByPrimaryKeySelective(afterSysUser);
            sysLogService.saveUserLog(beforeSysUser, afterSysUser);
        }
    }

    @Override
    public SysUser findSysUserByKeyword(String username) {
        return sysUserMapper.findSysUserByKeyword(username);
    }

    @Override
    public PageResult<SysUser> getUserList(Integer deptId, Integer pageNo, Integer pageSize) {
        //查询total
        int total = sysUserMapper.getCount(deptId);
        //查询 List<SysUser>
        List<SysUser> sysUserList = sysUserMapper.getUserList(deptId, (pageNo - 1) * pageSize, pageSize);
        PageResult<SysUser> pageResult = new PageResult<>(sysUserList, total);
        return pageResult;
    }

    @Override
    public List<SysRole> getRoleListByUserId(Integer userId) {
        List<Integer> roleIdListByUserId = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdListByUserId)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoleList = Lists.newArrayList();
        // 根据 roleIdListByUserId 查询 sysRole
        for (Integer roleId : roleIdListByUserId) {
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
            sysRoleList.add(sysRole);
        }
        return sysRoleList;
    }

        @Override
        public List<SysUser> getUserListBySysRoleList(List<SysRole> sysRoleList) {
            if(CollectionUtils.isEmpty(sysRoleList)) {
                return Lists.newArrayList();
            }
            Set<Integer> roleIdSet = sysRoleList.stream().map(sysRole -> sysRole.getId()).collect(Collectors.toSet());
          Set<SysUser> sysUserSet  = Sets.newHashSet();
        for (Integer roleId : roleIdSet) {
            List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
            List<SysUser> sysUserList = sysUserMapper.getUserListByUserIdList(userIdList);
            sysUserSet.addAll(sysUserList);
        }
        return Lists.newArrayList(sysUserSet);
    }

    /**
     * 校验邮箱是否已经存在
     *
     * @param mail
     * @return
     */
    private boolean checkMailExist(String mail, Integer userId) {
        int count = sysUserMapper.checkMailExist(mail, userId);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 校验手机号是否已经存在
     *
     * @param telephone
     * @param userId
     * @return
     */
    private boolean checkTelephoneExist(String telephone, Integer userId) {

        int count = sysUserMapper.checkTelephoneExist(telephone, userId);
        if (count <= 0) {
            return false;
        }
        return true;
    }


}
