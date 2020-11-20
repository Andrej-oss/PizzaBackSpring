package com.pizza_shop.project.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    private String SECURITY_KEY = "PiZzApRoJeCtMyFiRsTPrOjEcT";

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token){
return extractClaim(token, Claims::getExpiration);
    }
    public String extractName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    private Boolean isTokenExpiated(String token){
        return extractExpiration(token).before(new Date());
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY).compact();
    }
    public Boolean validateToken(String token, String username){
        final String extractName = extractName(token);
        return (extractName.equals(username)) && !isTokenExpiated(token);
    }
}
