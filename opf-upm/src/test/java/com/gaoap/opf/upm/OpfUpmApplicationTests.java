package com.gaoap.opf.upm;

import com.gaoap.opf.upm.entity.OpfUpmUser;
import com.gaoap.opf.upm.service.IOpfUpmUserService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@EnableEncryptableProperties
class OpfUpmApplicationTests {
    @Resource
    private IOpfUpmUserService opfUpmUserService;

    @Test
    void contextLoads() {

        List<OpfUpmUser> users = opfUpmUserService.list();
        System.out.println("users size:" + users.size());
        for (OpfUpmUser user : users) {
            String s = new BCryptPasswordEncoder().encode("admin");
            user.setPassword(s);
            boolean update = opfUpmUserService.updateById(user);
            System.out.println("s:" + s);
        }

    }

}
