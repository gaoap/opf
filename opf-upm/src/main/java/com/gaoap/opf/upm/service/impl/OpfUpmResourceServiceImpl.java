package com.gaoap.opf.upm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.mapper.OpfUpmResourceMapper;
import com.gaoap.opf.upm.security.annotation.ReloadResource;
import com.gaoap.opf.upm.service.IOpfUpmResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Service
@Slf4j
public class OpfUpmResourceServiceImpl extends ServiceImpl<OpfUpmResourceMapper, OpfUpmResource> implements IOpfUpmResourceService {

    @Override
    @ReloadResource
    public boolean add(OpfUpmResource opfUpmResource) {
        opfUpmResource.setCreateTime(new Date());
        return save(opfUpmResource);
    }

    @Override
    @ReloadResource
    public boolean update(Long id, OpfUpmResource opfUpmResource) {
        opfUpmResource.setId(id);
        boolean success = updateById(opfUpmResource);
        return success;
    }

    @Override
    @ReloadResource
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public Page<OpfUpmResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Page<OpfUpmResource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OpfUpmResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OpfUpmResource> lambda = wrapper.lambda();
        if (categoryId != null) {
            lambda.eq(OpfUpmResource::getCategoryId, categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)) {
            lambda.like(OpfUpmResource::getName, nameKeyword);
        }
        if (StrUtil.isNotEmpty(urlKeyword)) {
            lambda.like(OpfUpmResource::getUrl, urlKeyword);
        }
        return page(page, wrapper);
    }
}
