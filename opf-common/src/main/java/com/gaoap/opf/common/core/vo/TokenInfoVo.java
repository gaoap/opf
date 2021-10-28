package com.gaoap.opf.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户请求token时返回的token消息内容
 * @author gaoyd
 * @version 1.0.0
 * @ClassName TokenInfo.java
 * @Description TODO
 * @createTime 2021年10月27日 09:04:00
 */
@Data
@AllArgsConstructor
public class TokenInfoVo {
    //headers中储存token的key
    private String tokenHeader;
    // token的前缀
    private String tokenHead;
    //具体的token内容
    private String token;
}
