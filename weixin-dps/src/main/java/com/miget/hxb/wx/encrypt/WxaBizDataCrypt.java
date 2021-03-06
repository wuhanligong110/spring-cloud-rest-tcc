package com.miget.hxb.wx.encrypt;

import com.google.common.base.Charsets;
import com.miget.hxb.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;

/**
 * 微信小程序-加密数据解密算法
 */
public class WxaBizDataCrypt {
    private final String sessionKey;
    
    public WxaBizDataCrypt(String sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    /**
     * AES解密
     * @param encryptedData 密文
     * @return {String}
     */
    public String decrypt(String encryptedData, String ivStr) {
        byte[] bizData = Base64Utils.decodeBase64(encryptedData);
        byte[] keyByte = Base64Utils.decodeBase64(sessionKey);
        byte[] ivByte  = Base64Utils.decodeBase64(ivStr);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key sKeySpec = new SecretKeySpec(keyByte, "AES");
            // 初始化 
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
            byte[] original = cipher.doFinal(bizData);
            // 去除补位字符
            byte[] result = PKCS7Encoder.decode(original);
            return new String(result, Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("aes解密失败");
        }
    }
    
}
