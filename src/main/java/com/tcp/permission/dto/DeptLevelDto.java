package com.tcp.permission.dto;

import com.google.common.collect.Lists;
import com.tcp.permission.entity.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName DeptLevelDto
 * @Description: 适配出SysDept
 * {@link com.tcp.permission.entity.SysDept}
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptLevelDtoList = Lists.newArrayList();

    /**
     * 适配 sysDept
     *
     * @param sysDept
     * @return
     */
    public static DeptLevelDto adapt(SysDept sysDept) {
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        //将sysDept copy 到 deptLevelDto
        BeanUtils.copyProperties(sysDept, deptLevelDto);
        return deptLevelDto;
    }
}
