package com.tcp.permission.service.impl;

import com.tcp.permission.common.JsonMapper;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.constants.LogType;
import com.tcp.permission.dao.SysLogMapper;
import com.tcp.permission.entity.*;
import com.tcp.permission.param.SearchLogParam;
import com.tcp.permission.service.SysLogService;
import com.tcp.permission.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SysLogServiceImpl
 * @Description: 权限RBAC操作日志记录逻辑处理
 * @Author TCP
 * @Date 2020/6/5 0005
 * @Version V1.0
 **/
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleUserLog(Integer roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveRoleAclLog(Integer roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(new Date());
        sysLogMapper.insertSelective(sysLog);
    }

    @Override
    public List<SysLog> getSysLogList(Integer pageNo, Integer pageSize, SearchLogParam searchLogParam) {
        if(pageNo == null) {
            pageNo = 1;
        }
        pageNo = (pageNo-1)*pageNo;
       return sysLogMapper.getSysLogList(pageNo,pageSize,searchLogParam);
    }

    @Override
    public Integer getCount(SearchLogParam searchLogParam) {
        return sysLogMapper.getCount(searchLogParam);

    }
}
