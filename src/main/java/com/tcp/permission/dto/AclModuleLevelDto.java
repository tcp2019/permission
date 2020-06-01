package com.tcp.permission.dto;

import com.google.common.collect.Lists;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysAclModule;
import com.tcp.permission.entity.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName AclModuleLevelDto
 * @Description: 适配SysAclModule
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
@Getter
@Setter
@ToString

public class AclModuleLevelDto extends SysAclModule {
    private List<AclModuleLevelDto> aclModuleLevelDtoList = Lists.newArrayList();
    private List<SysAclDto> sysAclDtoList = Lists.newArrayList();

    /**
     * 适配 sysAclModule
     *
     * @param sysAclModule
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto aclModuleLevelDto = new AclModuleLevelDto();
        //将sysDept copy 到 deptLevelDto
        BeanUtils.copyProperties(sysAclModule, aclModuleLevelDto);
        return aclModuleLevelDto;
    }
}
