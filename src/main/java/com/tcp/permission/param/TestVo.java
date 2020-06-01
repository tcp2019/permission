package com.tcp.permission.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName TestVo
 * @Description: TODO
 * @Author TCP
 * @Date 2020/4/29 0029
 * @Version V1.0
 **/
@Data
public class TestVo {
    @NotNull
    @Max(value = 10L)
    @Min(value = 1L,message = "数值要大于0")
    private Integer id;

    @NotBlank()
    private String name;

}
