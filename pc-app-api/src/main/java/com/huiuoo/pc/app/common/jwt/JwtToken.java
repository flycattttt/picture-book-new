package com.huiuoo.pc.app.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录Token的生成和解析
 *
 */
public class JwtToken {

    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "huiuoo-picture-book-shanghai";
    /** jwt 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build jwt
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("user_id", null == user_id ? null : user_id.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static boolean verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
            if (jwt != null){
                return true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // jwt 校验失败, 抛出Token验证非法异常
        }
        //return jwt.getClaims();
        return false;
    }

    /**
     * 根据Token获取userId
     *
     * @param token
     * @return userId
     */
    public static long getUserId(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);

            Map<String, Claim> claims = jwt.getClaims();
            Claim user_id_claim = claims.get("user_id");
            if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
                return 0;
            }
            return Integer.valueOf(user_id_claim.asString());

        } catch (Exception e) {
            // e.printStackTrace();
            // jwt 校验失败, 抛出Token验证非法异常
        }

        return 0;
    }
    
    /**
     * 根据Token获取custId
     *
     * @param token
     * @return 根据Token获取custId
     */
    public static String getCustId(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
            
            Map<String, Claim> claims = jwt.getClaims();
            Claim custId = claims.get("custId");
            if (null == custId || StringUtils.isEmpty(custId.asString())) {
                return null;
            }
            return custId.asString();
            
        } catch (Exception e) {
            // e.printStackTrace();
            // jwt 校验失败, 抛出Token验证非法异常
        }
        
        return null;
    }
}

