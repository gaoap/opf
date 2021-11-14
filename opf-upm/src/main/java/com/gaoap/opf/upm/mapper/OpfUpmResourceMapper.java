package com.gaoap.opf.upm.mapper;

import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Mapper
public interface OpfUpmResourceMapper extends BaseMapper<OpfUpmResource> {
    /**
     * 对应映射文件：OpfUpmResourceMapper.xml
     * 获取用户所有可访问资源，
     *
     * @Param("userId")中的 userId对应xml中的#{userId}
     * 返回对象：List<OpfUpmResource>中的OpfUpmResource对应xml文件中的resultType
     * 方法名：getResourceList对应xml中id
     */
    List<OpfUpmResource> getResourceList(@Param("userId") Long userId);


    /**
     * ur.* 返回的字段会被映射为OpfUpmResource对象
     * #{userId}对应方法参数@Param("userId") Long userId
     *
     * @param userId
     * @return
     */
    @Select("  SELECT ur.* " +
            "        FROM opf_upm_user_role_relation ar " +
            "                 LEFT JOIN opf_upm_role r ON ar.role_id = r.id " +
            "                 LEFT JOIN opf_upm_role_resource_relation rrr ON r.id = rrr.role_id " +
            "                 LEFT JOIN opf_upm_resource ur ON ur.id = rrr.resource_id " +
            "        WHERE ar.user_id = #{userId} " +
            "          AND ur.id IS NOT NULL  GROUP by ur.id ")
    List<OpfUpmResource> getResourceListNoXML(@Param("userId") Long userId);

    /**
     * 根据角色ID获取资源
     */
    @Select("      SELECT r.* " +
            "        FROM opf_upm_role_resource_relation rrr " +
            "                 LEFT JOIN opf_upm_resource r ON rrr.resource_id = r.id " +
            "        WHERE rrr.role_id = #{roleId} " +
            "          AND r.id IS NOT NULL GROUP by r.id")
    List<OpfUpmResource> getResourceListByRoleId(@Param("roleId") Long roleId);

}
