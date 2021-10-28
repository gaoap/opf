package com.gaoap.opf.admin.mapper;

import com.gaoap.opf.admin.entity.SysSubsystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 微服务中，系统编号管理。方便授权 Mapper 接口
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-27
 */
@Mapper
public interface SysSubsystemMapper extends BaseMapper<SysSubsystem> {

}
