package com.gestionactivos.gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Clase utilitaria para generar y validar JWT.
 * Usa la librería io.jsonwebtoken (jjwt 0.12.6)
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long jwtRefreshExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    /**
     * Obtiene el subject (email o username) desde un token válido
     * @param token JWT
     * @return subject
     */
    public String getSubjectFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * Valida un token JWT
     * @param token JWT
     * @return true si es válido y no expirado
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Obtiene la fecha de expiración del token
     * @param token JWT
     * @return fecha de expiración
     */
    public Date getExpirationDate(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * Analiza el token JWT y devuelve sus claims
     * @param token JWT
     * @return Claims del token
     * @throws JwtException si el token no es válido
     */
    private Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getUserId(Claims claims) {
        return claims.get("user_id", String.class);
    }

    public String getRole(Claims claims) {
        return claims.get("rol", String.class);
    }

    public String getEmail(Claims claims) {
        return claims.get("email", String.class);
    }
    public Claims getClaims(String token) {
        return parseToken(token);
    }

}