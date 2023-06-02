package com.example.api.utils;

import com.example.api.common.AccessDeniedException;
import com.example.api.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    private static String secret = "This_is_secret";
    private static long expiryDuration = 60 * 60;
    public String generateJwt(User user){
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;

        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);
        //claims, hora inicio sesión, tiempo duracion, usuario
        Claims claims = Jwts.claims()
                        .setIssuer(user.getId().toString())
                        .setIssuedAt(issuedAt)
                        .setExpiration(expiryAt);

        //Agrega datos del usurario para la session
        claims.put("username", user.getUsername());
        // generate Claims
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public Claims verify(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            System.out.println(claims.get("username"));
            return claims;
        } catch (Exception e){
            throw new AccessDeniedException("Acceso denegado");
        }
    }
}
