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
 * 后台用户登录日志表
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@Getter
@Setter
@TableName("opf_upm_user_login_log")
@ApiModel(value = "OpfUpmUserLoginLog对象", description = "后台用户登录日志表")
public class OpfUpmUserLoginLog {

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Date createTime;

    private String ip;

    private String address;

    @ApiModelProperty("浏览器登录类型")
    private String userAgent;

    @ApiModelProperty("最后更新时间")
    private Date modifyTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("最后修改人")
    private Long modifyUser;


}
