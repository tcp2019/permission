package com.tcp.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName DeptParam
 * @Description: 接收Dept请求参数的类
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
@Getter
@Setter
@ToString
public class DeptParam {
    private Integer id;
    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15, min = 2, message = "部门名称长度要在2-15个字之间")
    private String name;
    private Integer seq;
    /**
     * 可以为空，首层默认为0
     */
    private Integer parentId = 0;
    @NotBlank(message = "备注长度需要在150个字以内")
    private String remark;
}
