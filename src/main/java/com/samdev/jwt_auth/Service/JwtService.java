package com.samdev.jwt_auth.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private String secret = " ";

    private String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()*24*60*60))
                .signWith(SignWithKey())
                .compact();

    }

    private SecretKey SignWithKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(SignWithKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user){
        String email = extractUsername(token);
        return user.equals(user.getUsername());
    }
    public boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
