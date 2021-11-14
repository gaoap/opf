package com.gaoap.opf.upm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 后台角色资源关系表
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Getter
@Setter
@TableName("opf_upm_role_resource_relation")
@ApiModel(value = "OpfUpmRoleResourceRelation对象", description = "后台角色资源关系表")
public class OpfUpmRoleResourceRelation {

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("资源ID")
    private Long resourceId;

    @ApiModelProperty("最后更新时间")
    private Date modifyTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("最后修改人")
    private Long modifyUser;


}
