package com.gaoap.opf.admin.security.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName RoleType.java
 * @Description TODO
 * @createTime 2021年10月26日 14:21:00
 */
@Getter
@Setter
public class RoleType {
    private String name;
    private int type = 1; //1代表角色 2代表资源
    private long id;

    public RoleType(String name) {
        this.name = name;
    }

    public RoleType(String name, long id, int type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        if (type == 1) {
            return "ROLE_" + name;
        } else {
            return   name;
        }

    }
}
