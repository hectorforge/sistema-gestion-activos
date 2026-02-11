package com.gestionactivos.security.infrastructure.persistence.usuario;

import com.gestionactivos.security.domain.usuario.utils.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tbl_usuarios",
        indexes = {
        @Index(name = "idx_usuario_nombre", columnList = "nombre"),
        @Index(name = "idx_usuario_email", columnList = "email", unique = true),
        @Index(name = "idx_usuario_rol", columnList = "rol")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "foto_de_perfil")
    private String fotoDePerfil;

    @Column(name = "password", nullable = false)
    private String pass;

    @Column(name = "es_activo", nullable = false)
    private boolean esActivo;

    @Column(name = "esta_eliminado", nullable = false)
    private boolean estaEliminado;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;
}
