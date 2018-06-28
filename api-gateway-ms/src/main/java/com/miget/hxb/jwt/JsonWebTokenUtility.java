
package com.miget.hxb.jwt;


import com.google.common.base.Objects;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * Token生成工具类
 * <p/>
 */
@Component
@NoArgsConstructor
public class JsonWebTokenUtility {

    private Logger logger = Logger.getLogger(JsonWebTokenUtility.class);
    private static final SignatureAlgorithm signatureAlgorithm;
    static {
        //算法
        signatureAlgorithm = SignatureAlgorithm.HS512;
    }
    private Key secretKey;
    /**
     * 秘钥
     */
    @Value("${jwt.encodedKey:L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==}")
    private String encodedKey;
    /**
     * 过期时间
     */
    @Value("${jwt.expireTime:120}")
    private Integer expire;

    public JsonWebTokenUtility initKey() {
        //密钥
        if (Objects.equal(null, secretKey)) {
            secretKey = deserializeKey(encodedKey);
        }
        return this;
    }

    /**
     * 创建jwt token
     *
     * @param authTokenDetails
     * @return
     */
    public String createJsonWebToken(AuthTokenDetails authTokenDetails) {
        String token =
                Jwts.builder().setSubject(authTokenDetails.getUserId().toString())
                        .claim("mobile", authTokenDetails.getMobile())
                        .setExpiration(buildExpirationDate(expire))
                        .signWith(getSignatureAlgorithm(),
                                getSecretKey()).compact();
        return token;
    }

    private Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        Key key = new SecretKeySpec(decodedKey, getSignatureAlgorithm().getJcaName());
        return key;
    }

    private Key getSecretKey() {
        return secretKey;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public AuthTokenDetails parseAndValidate(String token) {
        AuthTokenDetails authTokenDetails = null;
        try {
            Claims claims =
                    Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            String mobile = (String) claims.get("mobile");
            Date expirationDate = claims.getExpiration();
            authTokenDetails = new AuthTokenDetails(Long.valueOf(userId), mobile, expirationDate);
        } catch (JwtException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return authTokenDetails;
    }

    private String serializeKey(Key key) {
        String encodedKey =
                Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refeshToken(String token) {
        AuthTokenDetails authTokenDetails = parseAndValidate(token);
        if (authTokenDetails == null) {
            //表示token 已经过期或者不正确
            return null;
        } else {
            return createJsonWebToken(authTokenDetails);
        }
    }

    /**
     * 设定过期时间
     *
     * @return
     */
    private Date buildExpirationDate(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

}
