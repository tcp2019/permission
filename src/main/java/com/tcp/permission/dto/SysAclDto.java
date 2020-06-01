package com.tcp.permission.dto;

import com.tcp.permission.entity.SysAcl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName SysAclDto
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/25 0025
 * @Version V1.0
 **/
@Getter
@Setter
public class SysAclDto extends SysAcl {
    /**
     * 当前权限点是否被选中
     */
    private boolean checked;
    /**
     * 是否有该权限点的权限
     */
    private boolean hasAcl;
    /**
     * 当前权限点是否拥有超级管理员权限
     */
    private boolean hasSupAdmin = hasSupAdmin();

    public static SysAclDto adapt(SysAcl sysAcl) {
        SysAclDto sysAclDto = new SysAclDto();
        BeanUtils.copyProperties(sysAcl, sysAclDto);
        return sysAclDto;
    }

    private boolean hasSupAdmin() {
        return true;
    }
}
