package com.gaoap.opf.admin;

import com.gaoap.opf.admin.entity.SysUser;
import com.gaoap.opf.admin.service.ISysUserService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class OpfAdminApplicationTests {

    @Resource
    private ISysUserService sysUserService;
    @Test
    void contextLoads() {
        SysUser user= sysUserService.getById(2);
        String s=new BCryptPasswordEncoder().encode("liubei");
        user.setPassword(s);
        sysUserService.updateById(user);
        System.out.println("s:"+s);
    }

}
