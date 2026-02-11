package com.gestionactivos.security.infrastructure.security.adapter;

import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.ports.in.IJwtGeneratorInPort;
import com.gestionactivos.security.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtGeneratorAdapter implements IJwtGeneratorInPort {

    private final JwtUtils jwtUtils;

    @Override
    public String generateToken(Usuario usuario) {
        return jwtUtils.generateToken(usuario);
    }
}