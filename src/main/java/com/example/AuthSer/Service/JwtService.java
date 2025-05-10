package com.example.AuthSer.Service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.AuthSer.Model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) // optional, مش لازم لو هتحط كل حاجة كـ claims
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 ساعة
                .signWith(SECRET, SignatureAlgorithm.HS512) // ✅ مهم جداً تطابق الـ algorithm
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
