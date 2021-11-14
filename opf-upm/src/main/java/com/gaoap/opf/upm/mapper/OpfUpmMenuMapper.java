package com.gaoap.opf.upm.mapper;

import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Mapper
public interface OpfUpmMenuMapper extends BaseMapper<OpfUpmMenu> {
    /**
     * 根据用户ID获取菜单
     */
    @Select("        SELECT m.* " +
            "        FROM opf_upm_user_role_relation arr " +
            "                 LEFT JOIN opf_upm_role r ON arr.role_id = r.id " +
            "                 LEFT JOIN opf_upm_role_menu_relation rmr ON r.id = rmr.role_id " +
            "                 LEFT JOIN opf_upm_menu m ON rmr.menu_id = m.id " +
            "        WHERE arr.user_id = #{userId} " +
            "          AND m.id IS NOT NULL  GROUP BY m.id")
    List<OpfUpmMenu> getMenuList(@Param("userId") Long userId);

    /**
     * 根据角色ID获取菜单
     */
    @Select("        SELECT m.* " +
            "        FROM opf_upm_role_menu_relation rmr " +
            "                 LEFT JOIN opf_upm_menu m ON rmr.menu_id = m.id " +
            "        WHERE rmr.role_id = #{roleId} " +
            "          AND m.id IS NOT NULL  GROUP BY m.id")
    List<OpfUpmMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
}
