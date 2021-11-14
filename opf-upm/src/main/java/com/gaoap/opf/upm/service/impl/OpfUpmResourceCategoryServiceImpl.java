package com.gaoap.opf.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gaoap.opf.upm.entity.OpfUpmResourceCategory;
import com.gaoap.opf.upm.mapper.OpfUpmResourceCategoryMapper;
import com.gaoap.opf.upm.service.IOpfUpmResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源分类表 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Service
@Slf4j
public class OpfUpmResourceCategoryServiceImpl extends ServiceImpl<OpfUpmResourceCategoryMapper, OpfUpmResourceCategory> implements IOpfUpmResourceCategoryService {

    @Override
    public List<OpfUpmResourceCategory> listAll() {
        QueryWrapper<OpfUpmResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(OpfUpmResourceCategory::getSort);
        return list(wrapper);
    }

    @Override
    public boolean add(OpfUpmResourceCategory opfUpmResourceCategory) {
        opfUpmResourceCategory.setCreateTime(new Date());
        return save(opfUpmResourceCategory);
    }
}
