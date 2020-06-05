package com.tcp.permission.controller;

import com.tcp.permission.beans.PageResult;
import com.tcp.permission.common.ResponseData;
import com.tcp.permission.entity.SysLog;
import com.tcp.permission.param.SearchLogParam;
import com.tcp.permission.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName SysLogController
 * @Description: TODO
 * @Author TCP
 * @Date 2020/6/5 0005
 * @Version V1.0
 **/
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/log.page")
    public String logPage() {
        return "views/log";
    }

    @PostMapping("/page.json")
    @ResponseBody
    public ResponseData page(Integer pageNo, Integer pageSize, SearchLogParam searchLogParam) {
        Integer count = sysLogService.getCount(searchLogParam);
        List<SysLog> sysLogList = sysLogService.getSysLogList(pageNo, pageSize, searchLogParam);
        PageResult pageResult = new PageResult(sysLogList, count);
        return ResponseData.success(200, "", pageResult);
    }
}
