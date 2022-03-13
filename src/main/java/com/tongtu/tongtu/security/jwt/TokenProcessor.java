package com.tongtu.tongtu.security.jwt;


import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * 生成和处理token的工具类
 * 可以在application.yml中更改token的有效时间
 */
@Data
@ConfigurationProperties(prefix = "tongtu.token")
public class TokenProcessor {
    private Long tokenTime= 24 * 60 * 60 * 1000 * 7L;
    private String tokenSign="token";
    private Long registerTokenTime= (long) (30*60*1000);
    private String registerTokenSign="register";

    /**
     * create a login token
     * @param username username
     * @return login token
     */
    public String createToken(String username){
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenTime))
                .signWith(SignatureAlgorithm.HS512, tokenSign)
                .compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * decode a login token
     * @param token token
     * @return username if exists
     */
    public String decodeToken(String token){
        try {
            return Jwts.parser().setSigningKey(tokenSign).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * create a register token
     * @param username username
     * @return register token
     */
    public String createRegisterToken(String username){
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+registerTokenTime))
                .signWith(SignatureAlgorithm.HS512, registerTokenSign)
                .compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * decode a register token
     * @param token token
     * @return username if exists
     */
    public String decodeRegisterToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(registerTokenSign)
                    .parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return null;
        }

    }



}
