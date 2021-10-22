package com.gaoap.opf.admin.service;

import com.gaoap.opf.admin.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoap.opf.common.core.http.HttpResult;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
public interface ISysRoleService extends IService<SysRole> {
    public List<SysRole> getRoleByUserId(Long userId);
}
