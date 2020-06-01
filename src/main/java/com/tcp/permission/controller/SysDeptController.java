package com.tcp.permission.controller;

import com.tcp.permission.common.ResponseData;
import com.tcp.permission.dto.DeptLevelDto;
import com.tcp.permission.param.DeptParam;
import com.tcp.permission.service.SysDeptLevelTreeService;
import com.tcp.permission.service.SysDeptService;
import com.tcp.permission.service.SysTreeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysDeptController
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save.json")
    @ResponseBody
    public ResponseData saveDept(DeptParam deptParam) {
        sysDeptService.saveSysDept(deptParam);
        return ResponseData.success(200, "添加部门成功");
    }

    @GetMapping("/tree.json")
    @ResponseBody
    public ResponseData getDeptLevelTree() {
        List<DeptLevelDto> deptLevelDtoList = sysTreeService.getDeptLevelTree();
        return ResponseData.success(200, "成功", deptLevelDtoList);
    }

    @PostMapping("/update.json")
    @ResponseBody
    public ResponseData updateDept(DeptParam deptParam) {
        sysDeptService.updateSysDept(deptParam);
        return ResponseData.success(200, "更新部门成功");
    }

    @GetMapping("/dept.page")
    public String login() {
        return "views/dept";
    }

    @DeleteMapping("/delete.json")
    @ResponseBody
    public ResponseData delete(@Param("deptId") Integer deptId) {
        sysDeptService.deleteDeptByDeptId(deptId);
        return ResponseData.success(200, "删除部门成功");
    }


}
