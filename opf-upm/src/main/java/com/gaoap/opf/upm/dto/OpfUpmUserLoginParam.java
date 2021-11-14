package com.gaoap.opf.upm.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


/**
 * 用户登录参数
 */
@Getter
@Setter
@ApiModel(value = "OpfUpmUserLoginParam对象", description = "登录用户参数规范")
public class OpfUpmUserLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
