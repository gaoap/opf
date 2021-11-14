package com.gaoap.opf.upm.mapper;

import com.gaoap.opf.upm.entity.OpfUpmUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Mapper
public interface OpfUpmUserMapper extends BaseMapper<OpfUpmUser> {
    /**
     * 根据资源ID获取所有拥有此资源的用户ID
     */
    @Select("   SELECT DISTINCT ar.user_id " +
            "        FROM opf_upm_role_resource_relation rr " +
            "                 LEFT JOIN opf_upm_user_role_relation ar ON rr.role_id = ar.role_id " +
            "        WHERE rr.resource_id = #{resourceId}")
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);

}
