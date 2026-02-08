package com.App.TaskManageWebb.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION}")
    private long jwtExpirationMilliseconds;

    //generate token utility method
    public String generateToken(Authentication authentication){
           String username = authentication.getName();
           Date currentDate = new Date();
           Date expireDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);
    //to construct a jwt token
        // i saind the token with the secret key
        //decoded key because it's in encryption format
       return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();


    }
    private Key getKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    public String getUsernameFromToken(String token){
       return Jwts.parserBuilder()
               .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
