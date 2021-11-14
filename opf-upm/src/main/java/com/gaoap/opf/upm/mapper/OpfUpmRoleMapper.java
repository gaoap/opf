package com.gaoap.opf.upm.mapper;

import com.gaoap.opf.upm.entity.OpfUpmRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Mapper
public interface OpfUpmRoleMapper extends BaseMapper<OpfUpmRole> {
    /**
     * 获取用户下的所有角色
     */
    @Select(" select r.* " +
            "        from opf_upm_user_role_relation ar " +
            "                 left join opf_upm_role r on ar.role_id = r.id " +
            "        where ar.user_id = #{userId}")
    List<OpfUpmRole> getRoleList(@Param("userId") Long userId);
}
