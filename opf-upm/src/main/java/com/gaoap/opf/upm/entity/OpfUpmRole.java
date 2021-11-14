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
 * 后台用户角色表
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Getter
@Setter
@TableName("opf_upm_role")
@ApiModel(value = "OpfUpmRole对象", description = "后台用户角色表")
public class OpfUpmRole {

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("后台用户数量")
    private Integer userCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer status;

    private Integer sort;

    @ApiModelProperty("最后更新时间")
    private Date modifyTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("最后修改人")
    private Long modifyUser;


}
