package com.tcp.permission.controller;

import com.google.common.collect.Maps;
import com.tcp.permission.beans.PageResult;
import com.tcp.permission.common.ResponseData;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.param.SysAclParam;
import com.tcp.permission.service.SysAclService;
import com.tcp.permission.service.SysRoleAclService;
import com.tcp.permission.service.SysRoleService;
import com.tcp.permission.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AclController
 * @Description: 权限点控制类
 * @Author TCP
 * @Date 2020/5/21 0021
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/acl")
public class AclController {
    @Autowired
    private SysAclService sysAclService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

//    @GetMapping("/aclList.json")
//    @ResponseBody
//    public ResponseData getAcl(Integer aclId) {
//        SysAcl sysAcl = sysAclService.getAcl(aclId);
//        return ResponseData.success(200, "成功", sysAcl);
//    }

    @GetMapping("/page.json")
    @ResponseBody
    public ResponseData page(
            Integer aclModuleId, Integer pageSize, Integer pageNo) {
        PageResult<SysAcl> pageResult = sysAclService.aclList(aclModuleId, pageSize, pageNo);
        return ResponseData.success(200, "成功", pageResult);
    }

    @PostMapping("/save.json")
    @ResponseBody
    public ResponseData saveSysAcl(SysAclParam sysAclParam) {
        sysAclService.saveSysAcl(sysAclParam);
        return ResponseData.success(200, "添加权限点成功");
    }

    @PostMapping("/update.json")
    @ResponseBody
    public ResponseData updateSysAcl(SysAclParam sysAclParam) {
        sysAclService.updateAcl(sysAclParam);
        return ResponseData.success(200, "修改权限点成功");
    }

    @GetMapping("/aclList.json")
    @ResponseBody
    public ResponseData aclList(@RequestParam("aclId") Integer aclId) {
        // 根据aclId 查询出对应的角色
        List<SysRole> sysRoleList = sysRoleService.getRoleListByAclId(aclId);
        //根据角色列表查询出对应的用户信息
        List<SysUser> sysUserList = sysUserService.getUserListBySysRoleList(sysRoleList);
        Map<String, Object> map = Maps.newHashMap();
        map.put("roles", sysRoleList);
        map.put("users", sysUserList);
        return ResponseData.success(200, "获取权限点对应的角色和用户成功", map);
    }
}
