package com.dora.commonservice.utils;

import com.dora.commonservice.constants.ResponseStatus;
import com.dora.commonservice.entity.TokenModel;
import com.dora.commonservice.entity.UserAuthorityVO;
import com.dora.commonservice.exception.BusinessException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static String jwtSecret = null;
    private static int jwtAccessExpirationMs = 0;
    private static int jwtRefreshTokenExpirationMs = 0;


    public static void initConfig(String jwtSecret, int jwtAccessExpirationMs, int jwtRefreshTokenExpirationMs){
        if (JwtTokenUtil.jwtSecret == null){
            synchronized (JwtTokenUtil.class){
                if (JwtTokenUtil.jwtSecret == null){
                    JwtTokenUtil.jwtAccessExpirationMs = jwtAccessExpirationMs;
                    JwtTokenUtil.jwtSecret = jwtSecret;
                    JwtTokenUtil.jwtRefreshTokenExpirationMs = jwtRefreshTokenExpirationMs;
                }
            }
        }
    }


    /**
     * 生成用户token
     * @param user
     * @return
     */
    public static String generateToken(UserAuthorityVO user,String tokenType){
        int expTime = "access_token".equals(tokenType)?jwtAccessExpirationMs:jwtRefreshTokenExpirationMs;
        if (user == null)
            throw new BusinessException(ResponseStatus.USER_NOT_FOUND);
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("id", user.getId());
        claims.put("exp", generateExpirationDate(expTime).getTime());
        return generateToken(claims,tokenType);
    }

    /**
     * 根据负责生成JWT的token
     */
    private static String generateToken(Map<String, Object> claims, String tokenType) {
        int expTime = "access_token".equals(tokenType)?jwtAccessExpirationMs:jwtRefreshTokenExpirationMs;
        return Jwts.builder()
                .setClaims(claims)
                .setId(CommonUtils.getUUIDStr())
                .setExpiration(generateExpirationDate(expTime))
                .signWith(SignatureAlgorithm.HS512, generalKey())
                .compact();
    }

    // 由字符串生成加密key
    private static SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = jwtSecret.getBytes(); // Base64.decodeBase64(Base64.encodeBase64String(JWT_SECRET.getBytes()));
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 从token中获取JWT中的负载
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.info("token验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private static Date generateExpirationDate(int expTime) {
        return new Date(System.currentTimeMillis() + expTime * 1000);
    }

    /**
     * 判断token是否已经失效
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        if (expiredDate == null)
            return true;
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null)
            return null;
        return claims.getExpiration();
    }

    public static TokenModel token2tokenModal(String token){
        if (StringUtils.isEmpty(token))
            return null;
        Claims claims = getClaimsFromToken(token);
        if (claims == null)
            return null;
        UserAuthorityVO user = new UserAuthorityVO();
        user.setId((Long) claims.get("id"));
        TokenModel tokenModel = new TokenModel();
        tokenModel.setUser(user);
//        // 检查是否token是否在15分钟后过期，如果是则生成新的token
//        Date expiredDate = claims.getExpiration();
//        Date now = new Date(System.currentTimeMillis() + 15 * 1000 * 60);
//        if (now.after(expiredDate)){
//            tokenModel.setToken(JwtTokenUtil.generateToken(user));
//        }
        return tokenModel;
    }

}
