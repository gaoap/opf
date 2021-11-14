package com.gaoap.opf.upm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.exception.Asserts;
import com.gaoap.opf.upm.common.http.ResultCode;
import com.gaoap.opf.upm.dto.OpfUpmUserParam;
import com.gaoap.opf.upm.dto.UpdateOpfUpmUserPasswordParam;
import com.gaoap.opf.upm.entity.*;
import com.gaoap.opf.upm.mapper.OpfUpmResourceMapper;
import com.gaoap.opf.upm.mapper.OpfUpmRoleMapper;
import com.gaoap.opf.upm.mapper.OpfUpmUserLoginLogMapper;
import com.gaoap.opf.upm.mapper.OpfUpmUserMapper;
import com.gaoap.opf.upm.security.jwt.JwtTokenUtil;
import com.gaoap.opf.upm.security.ser.OpfUpmUserDetails;
import com.gaoap.opf.upm.service.IOpfUpmUserRoleRelationService;
import com.gaoap.opf.upm.service.IOpfUpmUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
//@Service声名为IOpfUpmUserService接口实现类，springboot 才会初始化这个类。
@Service
//引入日志log
@Slf4j
public class OpfUpmUserServiceImpl extends ServiceImpl<OpfUpmUserMapper, OpfUpmUser> implements IOpfUpmUserService {
    //JWT生成的辅助类：见com.gaoap.opf.upm.security.jwt.JwtTokenUtil
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    //在代码中src/main/java/com/gaoap/opf/upm/conf/GlobalBeanConfig.java
    //中声名为new BCryptPasswordEncoder()实现。
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OpfUpmUserLoginLogMapper loginLogMapper;
    @Autowired
    private IOpfUpmUserRoleRelationService userRoleRelationService;
    @Autowired
    private OpfUpmRoleMapper roleMapper;
    @Autowired
    private OpfUpmResourceMapper resourceMapper;

    /**
     * 根据用户名获取用户信息，OpfUpmUser是对应数据表opf_upm_user的实体类
     * 参考：src/main/java/com/gaoap/opf/upm/entity/OpfUpmUser.java
     *
     * @param username
     * @return OpfUpmUserDetails是UserDetails的一个自定义实现类，用户承载用户及认证信息
     */
    @Override
    public OpfUpmUser getUserByUsername(String username) {
        //MyBatis-Plus的增强用法，省去手动去配置mapper及*Mapper.xml
        QueryWrapper<OpfUpmUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmUser::getUsername, username);
        //List<T> list(Wrapper<T> queryWrapper) 这个方法，即是IService提供，由MyBatis-Plus自动实现
        //MyBatis-Plus能够自动实现很多此类方法，简化提供CRUD操作的代码量
        List<OpfUpmUser> userList = list(wrapper);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public OpfUpmUser register(OpfUpmUserParam opfUpmUserParam) {
        OpfUpmUser opfUpmUser = new OpfUpmUser();
        BeanUtils.copyProperties(opfUpmUserParam, opfUpmUser);
        opfUpmUser.setCreateTime(new Date());
        opfUpmUser.setStatus(1);
        //查询是否有相同用户名的用户
        QueryWrapper<OpfUpmUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmUser::getUsername, opfUpmUser.getUsername());
        List<OpfUpmUser> opfUpmUserList = list(wrapper);
        if (opfUpmUserList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(opfUpmUser.getPassword());
        opfUpmUser.setPassword(encodePassword);
        baseMapper.insert(opfUpmUser);
        return opfUpmUser;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            //UserDetails  是SpringSecurity需要的用户详情。这个是SpringSecurity框架的需要
            UserDetails userDetails = loadUserByUsername(username);
            //验证密码是否正确
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }
            //生成用户认证凭证，放入Spring Security的上下文中，Spring Security才会认为用户登陆成功
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //获取token
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:", e);
        }
        return token;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    public void insertLoginLog(String username) {
        OpfUpmUser user = getUserByUsername(username);
        if (user == null) return;
        OpfUpmUserLoginLog loginLog = new OpfUpmUserLoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        loginLog.setIp(ip);
        log.info("用户的远程地址是：{}", ip);
        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    public void updateLoginTimeByUsername(String username) {
        OpfUpmUser record = new OpfUpmUser();
        record.setLoginTime(new Date());
        QueryWrapper<OpfUpmUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmUser::getUsername, username);
        update(record, wrapper);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public Page<OpfUpmUser> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<OpfUpmUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OpfUpmUser> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OpfUpmUser> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(OpfUpmUser::getUsername, keyword);
            lambda.or().like(OpfUpmUser::getNickName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean update(Long id, OpfUpmUser User) {
        User.setId(id);
        OpfUpmUser rawUser = getById(id);
        if (rawUser.getPassword().equals(User.getPassword())) {
            //与原加密密码相同的不需要修改
            User.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(User.getPassword())) {
                User.setPassword(null);
            } else {
                User.setPassword(passwordEncoder.encode(User.getPassword()));
            }
        }
        boolean success = updateById(User);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public int updateRole(Long UserId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<OpfUpmUserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmUserRoleRelation::getUserId, UserId);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<OpfUpmUserRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                OpfUpmUserRoleRelation roleRelation = new OpfUpmUserRoleRelation();
                roleRelation.setUserId(UserId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
        }
        return count;
    }

    @Override
    public List<OpfUpmRole> getRoleList(Long userId) {
        return roleMapper.getRoleList(userId);
    }

    /**
     * 根据用户ID获取，当前用户所拥有的资源权限；
     * OpfUpmResource是对应数据表opf_upm_resource的实体类
     *
     * @param UserId
     * @return
     */
    @Override
    public List<OpfUpmResource> getResourceList(Long UserId) {
        List<OpfUpmResource> resourceList;
        //resourceMapper 对应src/main/java/com/gaoap/opf/upm/mapper/OpfUpmResourceMapper.java
        //是表的映射关系类：opf_upm_resource
        //对应的还有一个配套的XML描述文件：src/main/resources/xml/OpfUpmResourceMapper.xml
        //resourceMapper.getResourceList(UserId)是使用映射文件(XML)的实现
        // resourceList = resourceMapper.getResourceList(UserId);
        //resourceMapper.getResourceListXML(UserId)是不使用使用映射文件(XML)的实现
        resourceList = resourceMapper.getResourceListNoXML(UserId);
        return resourceList;
    }

    @Override
    public ResultCode updatePassword(UpdateOpfUpmUserPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return ResultCode.PARAMETER_ERROR;
        }
        QueryWrapper<OpfUpmUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(OpfUpmUser::getUsername, param.getUsername());
        List<OpfUpmUser> UserList = list(wrapper);
        if (CollUtil.isEmpty(UserList)) {
            return ResultCode.USER_NOT_FOUND;
        }
        OpfUpmUser OpfUpmUser = UserList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), OpfUpmUser.getPassword())) {
            return ResultCode.PASSWORD_ERROR;
        }
        OpfUpmUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(OpfUpmUser);
        return ResultCode.SUCCESS;
    }

    /**
     * @param username 用户登录名
     * @return OpfUpmUserDetails是UserDetails的一个自定义实现类，用户承载用户及认证信息
     */
    @Override
    public OpfUpmUserDetails loadUserByUsername(String username) {
        log.info("加载{}：用户信息", username);
        //获取用户信息，OpfUpmUser是对应数据表opf_upm_user的实体类
        //src/main/java/com/gaoap/opf/upm/entity/OpfUpmUser.java
        OpfUpmUser User = getUserByUsername(username);
        if (User != null) {
            //根据用户ID获取，当前用户所拥有的资源权限；
            //每一条 OpfUpmResource对应一个SpringSecurity中的 GrantedAuthority
            //OpfUpmResource是对应数据表opf_upm_resource的实体类
            List<OpfUpmResource> resourceList = getResourceList(User.getId());
            return new OpfUpmUserDetails(User, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
