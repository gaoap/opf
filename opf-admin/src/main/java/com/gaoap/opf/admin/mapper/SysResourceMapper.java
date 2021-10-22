package com.gaoap.opf.admin.mapper;

import com.gaoap.opf.admin.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaoap.opf.admin.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 资源管理：URL、权限等 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {
    @Select("SELECT resource.* FROM sys_resource  resource join sys_role_resource rs on rs.resource_id = resource.id where rs.role_id = #{roleId}")
    public List<SysResource> getResourceByRoleId(Long roleId);
}
