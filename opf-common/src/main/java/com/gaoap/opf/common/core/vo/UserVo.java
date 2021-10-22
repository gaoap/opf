package com.gaoap.opf.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName UserVo.java
 * @Description TODO
 * @createTime 2021年10月21日 22:21:00
 */
@Data
@AllArgsConstructor
public class UserVo {
    private Long userId;
    private String username;
    private String password;
}
