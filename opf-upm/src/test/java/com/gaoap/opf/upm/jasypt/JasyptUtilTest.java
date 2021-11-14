package com.gaoap.opf.upm.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName JasyptUtilTest.java
 * @Description TODO
 * @createTime 2021年10月31日 21:07:00
 */

public class JasyptUtilTest {
    /**
     * Jasypt生成加密结果
     *
     * @param password 配置文件中设定的加密盐值
     * @param value    加密值
     * @return
     */
    public static String encyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密盐值
     * @param value    解密密文
     * @return
     */
    public static String decyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        //算法
        config.setAlgorithm("PBEWithMD5AndDES");
        //
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    @Test
    public void outPrintln() {
        // 加密
        String encUserName = encyptPwd("jasypt", "upm");
        String encPwd = encyptPwd("jasypt", "Qianbosi2008");
        // 解密
        String decPwd = decyptPwd("jasypt", "sUeIOGRIrkw5S96iqQjDkQ==");
        System.out.println(encUserName);
        System.out.println(encPwd);
        System.out.println(decPwd);
    }

}
/**
 * 其中秘钥password是必须自己定义的。其他都可以不配置，因为有默认的配置：
 * <p>
 * Key	Required	Default Value
 * jasypt.encryptor.password	True	-
 * jasypt.encryptor.algorithm	False	PBEWITHHMACSHA512ANDAES_256
 * jasypt.encryptor.key-obtention-iterations	False	1000
 * jasypt.encryptor.pool-size	False	1
 * jasypt.encryptor.provider-name	False	SunJCE
 * jasypt.encryptor.provider-class-name	False	null
 * jasypt.encryptor.salt-generator-classname	False	org.jasypt.salt.RandomSaltGenerator
 * jasypt.encryptor.iv-generator-classname	False	org.jasypt.iv.RandomIvGenerator
 * jasypt.encryptor.string-output-type	False	base64
 * jasypt.encryptor.proxy-property-sources	False	false
 * jasypt.encryptor.skip-property-sources	False	empty list
 */

