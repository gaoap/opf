package com.gaoap.opf.upm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.entity.OpfUpmRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
public interface IOpfUpmRoleService extends IService<OpfUpmRole> {
    /**
     * 添加角色
     */
    boolean add(OpfUpmRole role);

    /**
     * 批量删除角色
     */
    boolean delete(List<Long> ids);

    /**
     * 分页获取角色列表
     */
    Page<OpfUpmRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<OpfUpmMenu> getMenuList(Long adminId);

    /**
     * 获取角色相关菜单
     */
    List<OpfUpmMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<OpfUpmResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenus(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResources(Long roleId, List<Long> resourceIds);
}
