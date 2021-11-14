package com.gaoap.opf.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.dto.OpfUpmMenuNode;
import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.gaoap.opf.upm.mapper.OpfUpmMenuMapper;
import com.gaoap.opf.upm.service.IOpfUpmMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Service
@Slf4j
public class OpfUpmMenuServiceImpl extends ServiceImpl<OpfUpmMenuMapper, OpfUpmMenu> implements IOpfUpmMenuService {
    @Override
    public boolean add(OpfUpmMenu opfUpmMenu) {
        opfUpmMenu.setCreateTime(new Date());
        //修改菜单层级
        updateLevel(opfUpmMenu);
        return save(opfUpmMenu);
    }

    /**
     * 修改菜单层级,逻辑为顶级菜单为0，其它菜单为父菜单层级+1
     */
    private void updateLevel(OpfUpmMenu opfUpmMenu) {
        if (opfUpmMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            opfUpmMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            OpfUpmMenu parentMenu = getById(opfUpmMenu.getParentId());
            if (parentMenu != null) {
                opfUpmMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                opfUpmMenu.setLevel(0);
            }
        }
    }

    @Override
    public boolean update(Long id, OpfUpmMenu opfUpmMenu) {
        opfUpmMenu.setId(id);
        updateLevel(opfUpmMenu);
        return updateById(opfUpmMenu);
    }

    @Override
    public Page<OpfUpmMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        Page<OpfUpmMenu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OpfUpmMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmMenu::getParentId, parentId)
                .orderByDesc(OpfUpmMenu::getSort);
        return page(page, wrapper);
    }

    /**
     * 递归算法生成树形菜单结构。
     * OpfUpmMenuNode 继承自OpfUpmMenu 添加子菜单属性：List<OpfUpmMenuNode> children
     *
     * @return
     */
    @Override
    public List<OpfUpmMenuNode> treeList() {
        //获取全部菜单
        List<OpfUpmMenu> menuList = list();
        List<OpfUpmMenuNode> result = menuList.stream()
                //过滤顶级菜单
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        OpfUpmMenu opfUpmMenu = new OpfUpmMenu();
        opfUpmMenu.setId(id);
        opfUpmMenu.setHidden(hidden);
        return updateById(opfUpmMenu);
    }

    /**
     * 将OpfUpmMenu转化为OpfUpmMenuNode并设置children属性
     * OpfUpmMenuNode 继承自OpfUpmMenu 添加子菜单属性：List<OpfUpmMenuNode> children
     */
    private OpfUpmMenuNode covertMenuNode(OpfUpmMenu menu, List<OpfUpmMenu> menuList) {
        OpfUpmMenuNode node = new OpfUpmMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<OpfUpmMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
