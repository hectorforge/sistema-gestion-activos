package com.gestionactivos.security.domain.usuario.ports.in;

import com.gestionactivos.security.domain.usuario.Usuario;

public interface IJwtGeneratorInPort {
    String generateToken(Usuario usuario);
}
