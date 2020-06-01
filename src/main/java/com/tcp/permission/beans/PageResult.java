package com.tcp.permission.beans;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PageResult<T> {
    private List<T> data = Lists.newArrayList();

    private int total;

}
