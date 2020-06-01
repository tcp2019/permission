package com.tcp.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.tcp.permission.dao.SysAclMapper;
import com.tcp.permission.dao.SysAclModuleMapper;
import com.tcp.permission.dao.SysDeptMapper;
import com.tcp.permission.dto.AclModuleLevelDto;
import com.tcp.permission.dto.DeptLevelDto;
import com.tcp.permission.dto.SysAclDto;
import com.tcp.permission.entity.SysAcl;
import com.tcp.permission.entity.SysAclModule;
import com.tcp.permission.entity.SysDept;
import com.tcp.permission.service.SysCoreService;
import com.tcp.permission.service.SysTreeService;
import com.tcp.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysTreeServiceImpl
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/19 0019
 * @Version V1.0
 **/
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public List<AclModuleLevelDto> getAclModuleLevelTree() {
        List<AclModuleLevelDto> aclModuleLevelDtoList = Lists.newArrayList();
        //获取所有的权限模块
        List<SysAclModule> sysAclModuleList = sysAclModuleMapper.getAllAclModule();
        for (SysAclModule sysAclModule : sysAclModuleList) {
            AclModuleLevelDto aclModuleLevelDto = AclModuleLevelDto.adapt(sysAclModule);
            aclModuleLevelDtoList.add(aclModuleLevelDto);
        }
        return aclModuleLevelDtoListToTree(aclModuleLevelDtoList);
    }

    private List<AclModuleLevelDto> aclModuleLevelDtoListToTree(List<AclModuleLevelDto> aclModuleLevelDtoList) {
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        //校验集合是否为空
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            return Lists.newArrayList();
        }
        //定义Multimap key:level value: [aclModuleLevelDto1,aclModuleLevelDto2]
        Multimap<String, AclModuleLevelDto> aclModuleLevelDtoMultimap = ArrayListMultimap.create();
        for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelDtoList) {
            aclModuleLevelDtoMultimap.put(aclModuleLevelDto.getLevel(), aclModuleLevelDto);
            if (LevelUtil.ROOT.equals(aclModuleLevelDto.getLevel())) {
                rootList.add(aclModuleLevelDto);
            }
        }
        //对 rootList 根据seq排序
        Collections.sort(rootList, aclModuleLevelDtoComparator);
        transformAclModuleLevelDtoList(rootList, LevelUtil.ROOT, aclModuleLevelDtoMultimap);

        return rootList;
    }

    private void transformAclModuleLevelDtoList(List<AclModuleLevelDto> aclModuleLevelDtoList, String Level, Multimap<String, AclModuleLevelDto> aclModuleLevelDtoMultimap) {
        if (CollectionUtils.isNotEmpty(aclModuleLevelDtoList)) {
            for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelDtoList) {
                //生成下一层级的level
                String nextLevel = LevelUtil.calculateLevel(aclModuleLevelDto.getLevel(), aclModuleLevelDto.getId());
                List<AclModuleLevelDto> aclModuleLevelDtoList1 = (List<AclModuleLevelDto>) aclModuleLevelDtoMultimap.get(nextLevel);
                Collections.sort(aclModuleLevelDtoList1, aclModuleLevelDtoComparator);
                aclModuleLevelDto.setAclModuleLevelDtoList(aclModuleLevelDtoList1);
                if (CollectionUtils.isNotEmpty(aclModuleLevelDtoList1)) {
                    transformAclModuleLevelDtoList(aclModuleLevelDtoList1, nextLevel, aclModuleLevelDtoMultimap);
                }
            }
        }
    }


    @Override
    public List<DeptLevelDto> getDeptLevelTree() {
        List<SysDept> sysDeptList = sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptLevelDtoList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            DeptLevelDto deptLevelDto = DeptLevelDto.adapt(sysDept);
            deptLevelDtoList.add(deptLevelDto);
        }
        return deptLevelDtoListToTree(deptLevelDtoList);
    }

    @Override
    public List<AclModuleLevelDto> roleTree(Integer roleId) {
        // 获取当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // 获取当前角色的权限
        List<SysAcl> roleAclList = sysCoreService.getCurrentRoleAclList(roleId);
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //去用户权限点和角色权限点的交集
        //获取所有的权限点
        List<SysAcl> sysAclList = sysAclMapper.getAllList();
        Set<SysAcl> sysAclSet = new HashSet<>(sysAclList);
        List<SysAclDto> sysAclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : sysAclSet) {
            SysAclDto sysAclDto = SysAclDto.adapt(sysAcl);
            if (roleAclIdSet.contains(sysAcl.getId())) {
                sysAclDto.setChecked(true);
            }
            if (userAclIdSet.contains(sysAcl.getId())) {
                sysAclDto.setHasAcl(true);
            }
            sysAclDtoList.add(sysAclDto);
        }
        return sysAclDtoListToTree(sysAclDtoList);
    }

    @Override
    public List<AclModuleLevelDto> userAclTree(Integer userId) {
        // 获取当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getUserAclListByUserId(userId);
        List<SysAclDto> sysAclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : userAclList) {
            SysAclDto sysAclDto = SysAclDto.adapt(sysAcl);
            sysAclDto.setChecked(true);
            sysAclDto.setHasAcl(true);
            sysAclDtoList.add(sysAclDto);
        }
        return sysAclDtoListToTree(sysAclDtoList);
    }

    /**
     * 根据所有的权限点和权限模块组成权限数
     *
     * @param sysAclDtoList
     * @return
     */
    private List<AclModuleLevelDto> sysAclDtoListToTree(List<SysAclDto> sysAclDtoList) {
        if (CollectionUtils.isEmpty(sysAclDtoList)) {
            return Lists.newArrayList();
        }
        // key:aclModuleId value: [sysAclDto1,sysAclDto2,...] Map<String,List<Object>
        Multimap<Integer, SysAclDto> sysAclDtoMultimap = ArrayListMultimap.create();
        for (SysAclDto sysAclDto : sysAclDtoList) {
            if (sysAclDto.getStatus() == 1) {
                sysAclDtoMultimap.put(sysAclDto.getAclModuleId(), sysAclDto);
            }
        }
        List<AclModuleLevelDto> aclModuleLevelTreeList = getAclModuleLevelTree();
        bindSysAclWithOrder(aclModuleLevelTreeList, sysAclDtoMultimap);
        return aclModuleLevelTreeList;
    }

    /**
     * 将权限点和权限模块绑定到一起，并根据seq排序
     *
     * @param aclModuleLevelTreeList
     * @param sysAclMultimap
     */
    private void bindSysAclWithOrder(List<AclModuleLevelDto> aclModuleLevelTreeList, Multimap<Integer, SysAclDto> sysAclMultimap) {
        if (CollectionUtils.isNotEmpty(aclModuleLevelTreeList)) {
            for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelTreeList) {
                List<SysAclDto> sysAclList = (List<SysAclDto>) sysAclMultimap.get(aclModuleLevelDto.getId());
                Collections.sort(sysAclList, sysAclDtoComparator);
                aclModuleLevelDto.setSysAclDtoList(sysAclList);
                bindSysAclWithOrder(aclModuleLevelDto.getAclModuleLevelDtoList(), sysAclMultimap);
            }
        }
    }

    /**
     * 根据层级组装deptLevelDtoList
     *
     * @param deptLevelDtoList
     * @return
     */
    private List<DeptLevelDto> deptLevelDtoListToTree(List<DeptLevelDto> deptLevelDtoList) {
        //校验集合是否为空
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Lists.newArrayList();
        }
        // key:level value: [deptLevelDto1,deptLevelDto2,...] Map<String,List<Object>
        Multimap<String, DeptLevelDto> deptLevelDtoMultiMap = ArrayListMultimap.create();
        //定义ROOT 层级的 deptLevelDtoList
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            //把 key为level value为对应的deptLevelDto 放到deptLevelDtoMultiMap中
            deptLevelDtoMultiMap.put(deptLevelDto.getLevel(), deptLevelDto);
            //判断是不是最顶层层级
            if (LevelUtil.ROOT.equals(deptLevelDto.getLevel())) {
                rootList.add(deptLevelDto);
            }
        }
        //根据seq对 rootList 从小到大排序
        Collections.sort(rootList, deptLevelDtoComparator);
        transformDeptLevelDtoList(rootList, LevelUtil.ROOT, deptLevelDtoMultiMap);
        return rootList;
    }

    /**
     * 递归查询出每个层级的集合
     *
     * @param deptLevelDtoList     当前层级的dept集合
     * @param level                当前集合所在的层级
     * @param deptLevelDtoMultiMap level->[deptLevelDto1,deptLevelDto2,...]
     */
    private void transformDeptLevelDtoList(List<DeptLevelDto> deptLevelDtoList, String level, Multimap<String, DeptLevelDto> deptLevelDtoMultiMap) {
        // 1.处理当前层级
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            //2. 获取到下一层级的level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<DeptLevelDto> tempList = (List<DeptLevelDto>) deptLevelDtoMultiMap.get(nextLevel);
            // 3.对deptLevelDtoList根据seq从小到大排序
            Collections.sort(tempList, deptLevelDtoComparator);
            // 4.设置下一层级部门
            deptLevelDto.setDeptLevelDtoList(tempList);
            if (CollectionUtils.isNotEmpty(tempList)) {
                //进入到下一层的处理
                transformDeptLevelDtoList(tempList, nextLevel, deptLevelDtoMultiMap);
            }
        }

    }


    private Comparator<? super DeptLevelDto> deptLevelDtoComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
    private Comparator<? super AclModuleLevelDto> aclModuleLevelDtoComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
    private Comparator<? super SysAclDto> sysAclDtoComparator = new Comparator<SysAclDto>() {
        @Override
        public int compare(SysAclDto o1, SysAclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
