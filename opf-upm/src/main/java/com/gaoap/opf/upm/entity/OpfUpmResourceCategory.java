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
 * 资源分类表
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Getter
@Setter
@TableName("opf_upm_resource_category")
@ApiModel(value = "OpfUpmResourceCategory对象", description = "资源分类表")
public class OpfUpmResourceCategory {

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("最后更新时间")
    private Date modifyTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("最后修改人")
    private Long modifyUser;


}
