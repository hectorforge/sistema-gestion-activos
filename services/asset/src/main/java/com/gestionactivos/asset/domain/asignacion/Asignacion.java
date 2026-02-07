package com.gestionactivos.asset.domain.asignacion;

import com.gestionactivos.asset.domain.asignacion.utils.EstadoAsignacion;

import java.time.LocalDate;
import java.util.UUID;

public class Asignacion {
    private UUID idAsignacion;
    private UUID idActivo;
    private UUID idUsuario;

    private LocalDate fechaAsignacion;
    private LocalDate fechaDevolucion;
    private EstadoAsignacion estadoAsignacion;
    private String observaciones;


    public Asignacion() {
    }

    public Asignacion(UUID idAsignacion, UUID idActivo, UUID idUsuario, LocalDate fechaAsignacion, LocalDate fechaDevolucion, EstadoAsignacion estadoAsignacion, String observaciones) {
        this.idAsignacion = idAsignacion;
        this.idActivo = idActivo;
        this.idUsuario = idUsuario;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoAsignacion = estadoAsignacion;
        this.observaciones = observaciones;
    }

    public UUID getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(UUID idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public UUID getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(UUID idActivo) {
        this.idActivo = idActivo;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoAsignacion getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(EstadoAsignacion estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}