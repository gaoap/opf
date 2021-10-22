package com.gaoap.opf.admin.mapper;

import com.gaoap.opf.admin.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色管理 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("SELECT role.*  FROM sys_role role join sys_user_role ur on role_id = ur.role_id where ur.user_id = #{userId}")
    public List<SysRole> getRoleByUserId(Long userId);
}
