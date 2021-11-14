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
 * 子系统记录表
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Getter
@Setter
@TableName("opf_upm_subsystem")
@ApiModel(value = "OpfUpmSubsystem对象", description = "子系统记录表")
public class OpfUpmSubsystem {

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("系统编号")
    private String subCode;

    @ApiModelProperty("系统名称")
    private String subName;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty("备注信息")
    private String note;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("最后修改人")
    private Long modifyUser;

    @ApiModelProperty("最后修改时间")
    private Date modifyTime;


}
