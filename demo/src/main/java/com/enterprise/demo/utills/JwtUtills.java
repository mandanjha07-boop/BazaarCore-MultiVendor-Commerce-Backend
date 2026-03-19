package com.enterprise.demo.utills;

import com.enterprise.demo.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtills {
    private final Key signINKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateToken(Customer customer){
        return Jwts.builder()
                .setSubject(customer.getEmail())
                .claim("role",customer.getRole().name())
                .claim("userId",customer.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(signINKey,SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUserName(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signINKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public Long extractUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signINKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }
    public String extractRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signINKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
    }
    public Date extractExpiration(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signINKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenValid(String token){

        try{
            Date expiry = extractExpiration(token);
            return expiry.after(new Date());
        }
        catch (Exception e){
            return false;
        }
    }


}
