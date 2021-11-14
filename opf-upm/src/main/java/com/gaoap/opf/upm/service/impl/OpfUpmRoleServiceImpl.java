package com.gaoap.opf.upm.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoap.opf.upm.entity.*;
import com.gaoap.opf.upm.mapper.OpfUpmMenuMapper;
import com.gaoap.opf.upm.mapper.OpfUpmResourceMapper;
import com.gaoap.opf.upm.mapper.OpfUpmRoleMapper;
import com.gaoap.opf.upm.service.IOpfUpmRoleMenuRelationService;
import com.gaoap.opf.upm.service.IOpfUpmRoleResourceRelationService;
import com.gaoap.opf.upm.service.IOpfUpmRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Service
@Slf4j
public class OpfUpmRoleServiceImpl extends ServiceImpl<OpfUpmRoleMapper, OpfUpmRole> implements IOpfUpmRoleService {

    @Autowired
    private IOpfUpmRoleMenuRelationService roleMenuRelationService;
    @Autowired
    private IOpfUpmRoleResourceRelationService roleResourceRelationService;
    @Autowired
    private OpfUpmMenuMapper menuMapper;
    @Autowired
    private OpfUpmResourceMapper resourceMapper;

    @Override
    public boolean add(OpfUpmRole role) {
        role.setCreateTime(new Date());
        role.setUserCount(0);
        role.setSort(0);
        return save(role);
    }

    @Override
    @Transactional
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public Page<OpfUpmRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<OpfUpmRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OpfUpmRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OpfUpmRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(OpfUpmRole::getName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public List<OpfUpmMenu> getMenuList(Long userId) {
        return menuMapper.getMenuList(userId);
    }

    @Override
    public List<OpfUpmMenu> listMenu(Long roleId) {
        return menuMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<OpfUpmResource> listResource(Long roleId) {
        return resourceMapper.getResourceListByRoleId(roleId);
    }


    /**
     * 先批量删除，在批量插入。需要事务控制
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @Override
    @Transactional
    public int allocMenus(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<OpfUpmRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmRoleMenuRelation::getRoleId, roleId);
        roleMenuRelationService.remove(wrapper);
        //批量插入新关系
        List<OpfUpmRoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            OpfUpmRoleMenuRelation relation = new OpfUpmRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }


    /**
     * 先批量删除，在批量插入。需要事务控制
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Override
    @Transactional
    public int allocResources(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<OpfUpmRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);
        //批量插入新关系
        List<OpfUpmRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            OpfUpmRoleResourceRelation relation = new OpfUpmRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        return resourceIds.size();
    }
}
