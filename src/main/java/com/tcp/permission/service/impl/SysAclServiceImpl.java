package com.tcp.permission.service.impl;

import com.tcp.permission.beans.PageResult;
import com.tcp.permission.common.RequestHolder;
import com.tcp.permission.dao.SysAclMapper;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.exception.ParamException;
import com.tcp.permission.param.SysAclParam;
import com.tcp.permission.service.SysAclService;
import com.tcp.permission.service.SysLogService;
import com.tcp.permission.util.BeanValidator;
import com.tcp.permission.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SysAclServiceImpl
 * @Description: 权限点逻辑处理类
 * @Author TCP
 * @Date 2020/5/21 0021
 * @Version V1.0
 **/
@Service
public class SysAclServiceImpl implements SysAclService {
    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public PageResult<SysAcl> aclList(Integer aclModuleId, Integer pageSize, Integer pageNo) {
        pageNo = (pageNo - 1) * pageSize;
        int count = sysAclMapper.getCount(aclModuleId);
        List<SysAcl> sysAclList = sysAclMapper.aclList(aclModuleId, pageSize, pageNo);
        return PageResult.<SysAcl>builder().total(count).data(sysAclList).build();
    }

    @Override
    public SysAcl getAcl(Integer aclId) {
        return sysAclMapper.selectByPrimaryKey(aclId);
    }

    @Override
    public void saveSysAcl(SysAclParam sysAclParam) {
        //校验参数
        BeanValidator.check(sysAclParam);
        //校验同一权限模块下权限点名称不能相同
        if (checkExist(null, sysAclParam.getName(), sysAclParam.getAclModuleId())) {
            throw new ParamException("同一权限模块下权限点名称不能相同");
        }
        SysAcl sysAcl = SysAcl.builder().aclModuleId(sysAclParam.getAclModuleId()).code(sysAclParam.getCode())
                .name(sysAclParam.getName()).remark(sysAclParam.getRemark()).status(sysAclParam.getStatus())
                .url(sysAclParam.getUrl()).type(sysAclParam.getType()).seq(sysAclParam.getSeq()).build();
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        sysAcl.setOperatorTime(new Date());
        sysAclMapper.insertSelective(sysAcl);
        sysLogService.saveAclLog(null, sysAcl);
    }

    @Override
    public void updateAcl(SysAclParam sysAclParam) {
        //校验参数
        BeanValidator.check(sysAclParam);
        //校验同一权限模块下权限点名称不能相同
        if (checkExist(sysAclParam.getId(), sysAclParam.getName(), sysAclParam.getAclModuleId())) {
            throw new ParamException("同一权限模块下权限点名称不能相同");
        }
        SysAcl beforeSysAcl = sysAclMapper.selectByPrimaryKey(sysAclParam.getId());
        SysAcl afterSysAcl = SysAcl.builder().id(sysAclParam.getId()).aclModuleId(sysAclParam.getAclModuleId())
                .code(sysAclParam.getCode()).name(sysAclParam.getName()).remark(sysAclParam.getRemark())
                .status(sysAclParam.getStatus()).url(sysAclParam.getUrl()).type(sysAclParam.getType()).seq(sysAclParam.getSeq()).build();
        afterSysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        afterSysAcl.setOperatorIp(IpUtil.getIpAddr(RequestHolder.getCurrentRequest()));
        afterSysAcl.setOperatorTime(new Date());
        sysAclMapper.updateByPrimaryKeySelective(afterSysAcl);
        sysLogService.saveAclLog(beforeSysAcl, afterSysAcl);
    }

    /**
     * 校验统一权限模块下权限点名称不能相同
     *
     * @param id
     * @param name
     * @param aclModuleId
     * @return
     */
    private boolean checkExist(Integer id, String name, Integer aclModuleId) {
        return sysAclMapper.countByaclModuleIdAndName(aclModuleId == null ? 0 : aclModuleId, name, id) > 0;
    }
}
