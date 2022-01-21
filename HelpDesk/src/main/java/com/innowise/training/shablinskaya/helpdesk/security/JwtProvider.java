package com.innowise.training.shablinskaya.helpdesk.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Component
public class JwtProvider {
    private static final Logger log = org.apache.log4j.Logger.getLogger(JwtProvider.class);

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (Exception e){
            log.info("Invalid Token");
            return false;
        }
    }

    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
