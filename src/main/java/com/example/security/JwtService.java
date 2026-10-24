package com.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;
    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.access-expiration-ms:900000}") long accessExpiration,
                      @Value("${jwt.refresh-expiration-ms:604800000}") long refreshExpiration) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) throw new IllegalArgumentException("jwt.secret must be at least 32 bytes");
        this.key = Keys.hmacShaKeyFor(bytes); this.accessExpiration = accessExpiration; this.refreshExpiration = refreshExpiration;
    }
    public String generateAccessToken(String username) { return generate(username, "access", accessExpiration); }
    public String generateRefreshToken(String username) { return generate(username, "refresh", refreshExpiration); }
    private String generate(String username, String type, long expiration) {
        Date now = new Date();
        return Jwts.builder().subject(username).claim("type", type).issuedAt(now).expiration(new Date(now.getTime() + expiration)).signWith(key).compact();
    }
    private Claims claims(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload(); }
    public String extractUsername(String token) { return claims(token).getSubject(); }
    public boolean isAccessTokenValid(String token, String username) {
        try { Claims claims = claims(token); return username.equals(claims.getSubject()) && "access".equals(claims.get("type", String.class)) && claims.getExpiration().after(new Date()); }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
    public boolean isRefreshTokenValid(String token, String username) {
        try { Claims claims = claims(token); return username.equals(claims.getSubject()) && "refresh".equals(claims.get("type", String.class)) && claims.getExpiration().after(new Date()); }
        catch (JwtException | IllegalArgumentException ex) { return false; }
    }
}
