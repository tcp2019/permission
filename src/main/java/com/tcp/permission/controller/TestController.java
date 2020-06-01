package com.tcp.permission.controller;

import com.tcp.permission.common.ApplicationContextHelper;
import com.tcp.permission.common.ResponseData;
import com.tcp.permission.dao.SysAclModuleMapper;
import com.tcp.permission.entity.SysAclModule;
import com.tcp.permission.exception.PermissionException;
import com.tcp.permission.param.TestVo;
import com.tcp.permission.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName TestController
 * @Description: TODO
 * @Author TCP
 * @Date 2020/4/28 0028
 * @Version V1.0
 **/
@Controller
@Slf4j
@RequestMapping("test")
public class TestController {
    @GetMapping("/hello.json")
    @ResponseBody
    public ResponseData hello() {
        log.info("====>hello World");
//        throw new PermissionException("非法请求");
        return ResponseData.success(200, "成功");
    }

    @GetMapping("/error.json")
    @ResponseBody
    public ResponseData error() {
        log.info("====>hello World");
        throw new PermissionException("非法请求");
//        return ResponseData.success(200, "成功");
    }

    @GetMapping("/validate.json")
    @ResponseBody
    public ResponseData validate(TestVo testVo) {
        log.info("validate");
        try {
            Map<String, String> validateMap = BeanValidator.validateObject(testVo);
            if (validateMap != null && validateMap.entrySet().size() > 0) {
                for (Map.Entry<String, String> entry : validateMap.entrySet()) {
                    log.info("{}=>{}", entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseData.success(200, "test validate");
    }

    @GetMapping("/testSql.json")
    @ResponseBody
    public ResponseData testSql(TestVo testVo) {
        log.info("test sql");
        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info("sysAclModule:{}", sysAclModule);
        BeanValidator.check(testVo);
        return ResponseData.success(200, "test sql");
    }
}
