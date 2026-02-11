package com.gestionactivos.security.domain.usuario.ports.in;

public interface IPasswordEncoderInPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
