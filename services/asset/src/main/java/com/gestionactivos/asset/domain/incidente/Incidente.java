package com.gestionactivos.asset.domain.incidente;

import com.gestionactivos.asset.domain.incidente.utils.EstadoIncidente;
import com.gestionactivos.asset.domain.incidente.utils.TipoIncidente;

import java.time.LocalDateTime;
import java.util.UUID;

public class Incidente {
    private UUID idIncidente;
    private UUID idActivo;
    private UUID idUsuario;
    private TipoIncidente tipoIncidente;
    private String descripcion;
    private LocalDateTime fechaReporte;
    private EstadoIncidente estadoIncidente;

    public Incidente() {
    }

    public Incidente(UUID idIncidente, UUID idActivo, UUID idUsuario, TipoIncidente tipoIncidente, String descripcion, LocalDateTime fechaReporte, EstadoIncidente estadoIncidente) {
        this.idIncidente = idIncidente;
        this.idActivo = idActivo;
        this.idUsuario = idUsuario;
        this.tipoIncidente = tipoIncidente;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.estadoIncidente = estadoIncidente;
    }

    public UUID getIdIncidente() {
        return idIncidente;
    }

    public void setIdIncidente(UUID idIncidente) {
        this.idIncidente = idIncidente;
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

    public TipoIncidente getTipoIncidente() {
        return tipoIncidente;
    }

    public void setTipoIncidente(TipoIncidente tipoIncidente) {
        this.tipoIncidente = tipoIncidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public EstadoIncidente getEstadoIncidente() {
        return estadoIncidente;
    }

    public void setEstadoIncidente(EstadoIncidente estadoIncidente) {
        this.estadoIncidente = estadoIncidente;
    }
}
