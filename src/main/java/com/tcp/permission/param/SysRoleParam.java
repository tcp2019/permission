package com.tcp.permission.param;

import com.tcp.permission.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @ClassName SysRoleParam
 * @Description: 角色参数类
 * @Author TCP
 * @Date 2020/5/22 0022
 * @Version V1.0
 **/
@Getter
@Setter
public class SysRoleParam extends SysRole {
    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 10, message = "角色名称长度要在2-10个字符之间")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Length(min = 2, max = 100, message = "备注要在2-100个字符之间")
    private String remark;

}
