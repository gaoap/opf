package com.gaoap.opf.upm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
public interface IOpfUpmResourceService extends IService<OpfUpmResource> {
    /**
     * 添加资源
     */
    boolean add(OpfUpmResource opfUpmResource);

    /**
     * 修改资源
     */
    boolean update(Long id, OpfUpmResource opfUpmResource);

    /**
     * 删除资源
     */
    boolean delete(Long id);

    /**
     * 分页查询资源
     */
    Page<OpfUpmResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
