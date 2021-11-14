package com.gaoap.opf.upm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.http.ResultCode;
import com.gaoap.opf.upm.dto.OpfUpmUserParam;
import com.gaoap.opf.upm.dto.UpdateOpfUpmUserPasswordParam;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.entity.OpfUpmRole;
import com.gaoap.opf.upm.entity.OpfUpmUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
public interface IOpfUpmUserService extends IService<OpfUpmUser> {
    /**
     * 根据用户名获取后台管理员
     */
    OpfUpmUser getUserByUsername(String username);

    /**
     * 注册功能
     */
    OpfUpmUser register(OpfUpmUserParam OpfUpmUserParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Page<OpfUpmUser> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    boolean update(Long id, OpfUpmUser user);

    /**
     * 删除指定用户
     */
    boolean delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<OpfUpmRole> getRoleList(Long userId);

    /**
     * 获取指定用户的可访问资源
     */
    List<OpfUpmResource> getResourceList(Long userId);

    /**
     * 修改密码
     */
    ResultCode updatePassword(UpdateOpfUpmUserPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 添加登录记录
     */
    public void insertLoginLog(String username);

    /**
     * 根据用户名修改登录时间
     */
    public void updateLoginTimeByUsername(String username);

}
