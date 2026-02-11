package com.gestionactivos.security.domain.usuario;

import com.gestionactivos.security.domain.usuario.utils.Rol;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String fotoDePerfil;
    private String pass;
    private boolean esActivo;
    private boolean estaEliminado;

    private Rol rol;

    public Usuario() {
    }

    public Usuario(UUID id, String nombre, String apellido, String email, String fotoDePerfil, String pass, boolean esActivo, Rol rol, boolean estaEliminado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fotoDePerfil = fotoDePerfil;
        this.pass = pass;
        this.esActivo = esActivo;
        this.rol = rol;
        this.estaEliminado = estaEliminado;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(String fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isEsActivo() {
        return esActivo;
    }

    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEstaEliminado() {
        return estaEliminado;
    }

    public void setEstaEliminado(boolean estaEliminado) {
        this.estaEliminado = estaEliminado;
    }
}