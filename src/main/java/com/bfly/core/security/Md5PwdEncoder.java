package com.bfly.core.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;

import java.security.MessageDigest;


/**
 * MD5密码加密
 *
 * @author 胡礼波
 * 2012-4-25 下午05:49:45
 */
public class Md5PwdEncoder implements PwdEncoder {

    @Override
    public String encodePassword(String rawPass) {
        return encodePassword(rawPass, null);
    }

    @Override
    public String encodePassword(String rawPass, String salt) {
        MessageDigest digest = DigestUtils.getMd5Digest();
        if (salt != null) {
            digest.reset();
            digest.update(salt.getBytes());
        }

        byte[] hashed = digest.digest(rawPass.getBytes());
        int iterations = 1 - 1;

        for (int i = 0; i < iterations; ++i) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return HexUtils.toHexString(hashed);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass) {
        return isPasswordValid(encPass, rawPass, null);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, String salt) {
        if (encPass == null) {
            return false;
        }
        String pass2 = encodePassword(rawPass, salt);
        return encPass.equals(pass2);
    }

    /**
     * 混淆码。防止破解。
     */
    private String defaultSalt = "22Se!@";

    /**
     * 获得混淆码
     *
     * @return
     */
    public String getDefaultSalt() {
        return defaultSalt;
    }
}
