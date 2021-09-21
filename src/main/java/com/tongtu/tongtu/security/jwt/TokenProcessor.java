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
    private Long tokenTime= (long) (24 * 60 * 60 * 1000);
    private String tokenSign="token";

    public String createToken(String username){
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenTime))
                .signWith(SignatureAlgorithm.HS512, tokenSign)
                .compressWith(CompressionCodecs.GZIP).compact();
    }

    public String decodeToken(String token){
        try {
            String name = Jwts.parser().setSigningKey(tokenSign).parseClaimsJws(token).getBody().getSubject();
            return name;
        }catch (Exception e){
            return null;
        }


    }
}
