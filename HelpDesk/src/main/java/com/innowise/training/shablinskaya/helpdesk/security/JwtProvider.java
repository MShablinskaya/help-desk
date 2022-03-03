package com.innowise.training.shablinskaya.helpdesk.security;

import com.innowise.training.shablinskaya.helpdesk.enums.Role;
import com.innowise.training.shablinskaya.helpdesk.security.details.CustomDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private CustomDetailsServiceImpl userDetailService;

    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    public JwtProvider(CustomDetailsServiceImpl userDetailService){
        this.userDetailService = userDetailService;
    }

//    protected void init(){
//        secret = Base64.getEncoder().encodeToString(secret.getBytes());
//    }

    public String createToken(String username, List<Role> role){

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", getRolesNames(role));

        Date now = new Date();
        Date validity = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWToken is expired or invalid");
        }
    }

    private List<String> getRolesNames(List<Role> roles){
        List<String> result = new ArrayList<>();

        roles.forEach(role -> {
            result.add(role.name());
        });

        return result;
    }
}
