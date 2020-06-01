package com.tcp.permission.controller;

import com.tcp.permission.common.ResponseData;
import com.tcp.permission.dto.AclModuleLevelDto;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.entity.SysRoleUser;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.param.SysAclModuleParam;
import com.tcp.permission.param.SysRoleParam;
import com.tcp.permission.service.SysRoleAclService;
import com.tcp.permission.service.SysRoleService;
import com.tcp.permission.service.SysRoleUserService;
import com.tcp.permission.service.SysTreeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleController
 * @Description: 角色控制层
 * @Author TCP
 * @Date 2020/5/22 0022
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysRoleUserService sysRoleUserService;


    @GetMapping("/list.json")
    @ResponseBody
    public ResponseData list() {
        List<SysRole> sysRoleList = sysRoleService.getSysRoleList();
        return ResponseData.success(200, "成功", sysRoleList);
    }

    @PostMapping("/save.json")
    @ResponseBody
    public ResponseData saveSysRole(SysRoleParam sysRoleParam) {
        sysRoleService.saveSysRole(sysRoleParam);
        return ResponseData.success(200, "添加角色成功");
    }

    @PostMapping("/update.json")
    @ResponseBody
    public ResponseData updateSysRole(SysRoleParam sysRoleParam) {
        sysRoleService.updateSysRole(sysRoleParam);
        return ResponseData.success(200, "修改角色成功");
    }

    @GetMapping("/role.page")
    public String role() {
        return "views/role";
    }

    @PostMapping("/roleTree.json")
    @ResponseBody
    public ResponseData roleTree(@RequestParam("roleId") Integer roleId) {
        List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.roleTree(roleId);
        return ResponseData.success(200, "成功", aclModuleLevelDtoList);
    }

    /**
     * 添加或者修改角色权限
     *
     * @param roleId
     * @param aclIds
     * @return
     */
    @PostMapping("/changeRoleAcl.json")
    @ResponseBody
    public ResponseData changeRoleAcl(@RequestParam("roleId") Integer roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        sysRoleAclService.changeRoleAcl(roleId, aclIds);
        return ResponseData.success(200, "保存成功");
    }

    /**
     * 根据roleId 查询出所有已分配该角色得到用户和未分配该角色的用户
     *
     * @param roleId
     * @return
     */
    @PostMapping("/users.json")
    @ResponseBody
    public ResponseData users(@RequestParam("roleId") Integer roleId) {
        Map<String, List<SysUser>> sysUserMap = sysRoleUserService.users(roleId);
        return ResponseData.success(200, "保存成功", sysUserMap);
    }

    @PostMapping("/changeUsers.json")
    @ResponseBody
    public ResponseData changeUsers(@RequestParam("roleId") Integer roleId, @Param("userIds") String userIds) {
        sysRoleUserService.changeUsers(roleId, userIds);
        return ResponseData.success(200, "添加用户的角色权限成功");
    }
}
