package com.gestionactivos.security.infrastructure.security.jwt;

import com.gestionactivos.security.domain.usuario.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Clase utilitaria para generar y validar JWT.
 * Usa la librería io.jsonwebtoken (jjwt 0.12.6)
 */
@Component
@RequiredArgsConstructor
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
     * Genera un token JWT para un usuario
     * @param usuario normalmente el email o username
     * @return token JWT firmado
     */
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String iniciales = usuario.getNombre().substring(0,1).toUpperCase() + usuario.getApellido().substring(1);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("nombre_completo", String.format("%s %s", usuario.getNombre(), usuario.getApellido()))
                .claim("apellido", usuario.getApellido())
                .claim("nombre", usuario.getNombre())
                .claim("nombre_completo_iniciales", iniciales)
                .claim("foto_perfil", usuario.getFotoDePerfil())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
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
}