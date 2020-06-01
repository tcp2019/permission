package com.tcp.permission.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @ClassName SysAclModuleParam
 * @Description: 权限模块参数类
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAclModuleParam {

    private Integer id;
    @NotBlank(message = "权限名称不能为空")
    @Length(max = 10, min = 2)
    private String name;

    @Length(max = 100, message = "备注长度要在100个字符以内")
    private String remark;
    private Integer parentId = 0;

    @NotNull(message = "当前层级下的权限模块顺序不能为空")
    private Integer seq;

}
