package com.tcp.permission.common;


import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ResponseData
 * @Description: TODO
 * @Author TCP
 * @Date 2020/4/29 0029
 * @Version V1.0
 **/
@Data
public class ResponseData {
    private String msg;
    private int status;
    private Object data;

    public static ResponseData success(int status, String msg, Object data) {
        ResponseData responseData = new ResponseData();
        responseData.status = status;
        responseData.data = data;
        responseData.msg = msg;
        return responseData;
    }

    public static ResponseData success(int status, String msg) {
        ResponseData responseData = new ResponseData();
        responseData.status = status;
        responseData.msg = msg;
        return responseData;
    }

    public static ResponseData fail(int status, String msg) {
        ResponseData responseData = new ResponseData();
        responseData.status = status;
        responseData.msg = msg;
        return responseData;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> responseDataMap = new HashMap<>(16);
        responseDataMap.put("status", this.status);
        responseDataMap.put("msg", this.msg);
        responseDataMap.put("data", this.data);
        return responseDataMap;

    }
}
