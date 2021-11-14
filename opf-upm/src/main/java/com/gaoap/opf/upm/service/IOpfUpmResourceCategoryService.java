package com.gaoap.opf.upm.service;

import com.gaoap.opf.upm.entity.OpfUpmResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
public interface IOpfUpmResourceCategoryService extends IService<OpfUpmResourceCategory> {
    /**
     * 获取所有资源分类
     */
    List<OpfUpmResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean add(OpfUpmResourceCategory opfUpmResourceCategory);
}
