package com.tcp.permission.controller;

import com.google.common.collect.Maps;
import com.tcp.permission.beans.PageResult;
import com.tcp.permission.common.ResponseData;
import com.tcp.permission.dto.AclModuleLevelDto;
import com.tcp.permission.entity.SysRole;
import com.tcp.permission.entity.SysUser;
import com.tcp.permission.param.UserParam;
import com.tcp.permission.service.SysTreeService;
import com.tcp.permission.service.SysUserService;
import javafx.beans.DefaultProperty;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserController
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save.json")
    @ResponseBody
    public ResponseData saveSysUser(UserParam userParam) {
        sysUserService.saveSysUser(userParam);
        return ResponseData.success(200, "添加用户信息成功");
    }

    @PostMapping("/update.json")
    @ResponseBody
    public ResponseData updateSysUser(UserParam userParam) {
        sysUserService.updateSysUser(userParam);
        return ResponseData.success(200, "修改用户信息成功");
    }

    @GetMapping("/list.json")
    @ResponseBody
    public ResponseData getUserList(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, Integer deptId) {
        PageResult<SysUser> pageResult = sysUserService.getUserList(deptId, pageNo, pageSize);
        return ResponseData.success(200, "获取用户信息成功", pageResult);
    }

    @GetMapping("/acls.json")
    @ResponseBody
    public ResponseData acls(@Param("userId") Integer userId) {
        List<SysRole> sysRoleList = sysUserService.getRoleListByUserId(userId);
        List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.userAclTree(userId);
        Map<String, Object> map = Maps.newHashMap();
        map.put("roles", sysRoleList);
        map.put("acls", aclModuleLevelDtoList);
        return ResponseData.success(200, "获取用户信息成功", map);
    }

    @GetMapping("/noAuth.page")
    public String noAuth() {
        return "views/noAuth";
    }

}
