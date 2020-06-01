package com.tcp.permission.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SysAclParam
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/21 0021
 * @Version V1.0
 **/
@Getter
@Setter
public class SysAclParam {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private String remark;

    private Integer seq;

}
