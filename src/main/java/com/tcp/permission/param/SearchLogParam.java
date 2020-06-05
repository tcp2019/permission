package com.tcp.permission.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SearchLogParam
 * @Description: TODO
 * @Author TCP
 * @Date 2020/6/5 0005
 * @Version V1.0
 **/
@Getter
@Setter
public class SearchLogParam {
    private String beforeSeg;
    private String afterSeg;
    private Integer type;
    private String fromTime;
    private String toTime;
    private String operator;
}
