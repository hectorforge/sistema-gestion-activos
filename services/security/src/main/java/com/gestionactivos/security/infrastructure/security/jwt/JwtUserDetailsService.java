package com.gestionactivos.security.infrastructure.security.jwt;

import com.gestionactivos.security.domain.usuario.Usuario;
import com.gestionactivos.security.domain.usuario.ports.out.IUsuarioRepositoryOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Servicio de Spring Security que carga usuario por email y mapea a UserDetails
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUsuarioRepositoryOutPort repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return mapUsuarioToUserDetails(usuario);
    }

    /**
     * Mapea un Usuario de dominio a un UserDetails de Spring Security
     *
     * @param usuario Usuario de dominio
     * @return UserDetails con username, password, roles y estado
     */
    private UserDetails mapUsuarioToUserDetails(Usuario usuario) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
        );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPass())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!usuario.isEsActivo())
                .build();
    }
}