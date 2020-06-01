package com.tcp.permission.controller;

import com.tcp.permission.common.ResponseData;
import com.tcp.permission.dto.AclModuleLevelDto;
import com.tcp.permission.dto.DeptLevelDto;
import com.tcp.permission.param.SysAclModuleParam;
import com.tcp.permission.service.SysAclModuleLevelTreeService;
import com.tcp.permission.service.SysAclModuleService;
import com.tcp.permission.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName SysAclModuleController
 * @Description: 权限模块控制类
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {
    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save.json")
    @ResponseBody
    public ResponseData saveSysAclModule(SysAclModuleParam sysAclModuleParam) {
        sysAclModuleService.saveSysAclModule(sysAclModuleParam);
        return ResponseData.success(200, "添加部门成功");
    }

    @GetMapping("/tree.json")
    @ResponseBody
    public ResponseData getDeptLevelTree() {
        List<AclModuleLevelDto> aclModuleLevelDtoList = sysTreeService.getAclModuleLevelTree();
        return ResponseData.success(200, "成功", aclModuleLevelDtoList);
    }

    @PostMapping("/update.json")
    @ResponseBody
    public ResponseData updateDept(SysAclModuleParam sysAclModuleParam) {
        sysAclModuleService.updateSysAclModule(sysAclModuleParam);
        return ResponseData.success(200, "更新部门成功");
    }

    @GetMapping("/aclModule.page")
    public String login() {
        return "views/acl";
    }
}
