package com.gaoap.opf.upm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.dto.OpfUpmMenuNode;
import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
public interface IOpfUpmMenuService extends IService<OpfUpmMenu> {
    /**
     * 添加后台菜单
     */
    boolean add(OpfUpmMenu opfUpmMenu);

    /**
     * 修改后台菜单
     */
    boolean update(Long id, OpfUpmMenu opfUpmMenu);

    /**
     * 分页查询后台菜单
     */
    Page<OpfUpmMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<OpfUpmMenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    boolean updateHidden(Long id, Integer hidden);
}
