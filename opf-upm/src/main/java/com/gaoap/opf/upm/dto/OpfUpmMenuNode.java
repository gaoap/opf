package com.gaoap.opf.upm.dto;


import com.gaoap.opf.upm.entity.OpfUpmMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 菜单封装，主要是存储菜单树。相关逻辑见：
 * com.gaoap.opf.upm.service.impl.OpfUpmMenuServiceImpl
 * #covertMenuNode(com.gaoap.opf.upm.entity.OpfUpmMenu, java.util.List)
 */
@Getter
@Setter
@ApiModel(value = "OpfUpmMenuNode对象", description = "菜单节点规范")
public class OpfUpmMenuNode extends OpfUpmMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<OpfUpmMenuNode> children;
}
