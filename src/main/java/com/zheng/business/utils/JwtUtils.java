package com.zheng.business.utils;

import com.zheng.business.bean.RrException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static String secret="dfjlaer";
    //一个星期
    private static long expire=604800l;
    private static String header;

    /**
     * 生成jwt token 失效时间为expire * 1000
     */
    public static String generateToken(String phone,int type) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder().claim("phone",phone)
                .claim("type",type)
                .setHeaderParam("typ", "JWT")
                //              .setId(companyId)
                //               .setSubject(userId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /**
     * 解析token，可得到claim
     */
    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException ex){
            throw new RrException(30002,"token已过期！");
        }
        catch (RuntimeException e){
            throw new RrException(30000,"token解析异常！");
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
