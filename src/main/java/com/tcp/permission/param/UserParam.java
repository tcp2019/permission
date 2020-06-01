package com.tcp.permission.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName UserParam
 * @Description: SysUser 接收参数类
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
@Getter
@Setter
@ToString
@Data
public class UserParam {
    private Integer id;

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 10, message = "用户名长度需要在10个字符以内")
    private String username;

    @NotBlank(message = "电话不可以为空")
    @Length(min = 1, max = 13, message = "手机号长度需要在13个字符以内")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    private String mail;

    @NotBlank(message = "备注不能为空")
    @Length(min = 1, max = 100, message = "备注长度需要在100个字符以内")
    private String remark;

    @NotNull(message = "用户所在部门不能为空")
    private Integer deptId;

    @NotNull(message = "用户状态不能为空")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 1, message = "用户状态不合法")
    private Integer status;


}
