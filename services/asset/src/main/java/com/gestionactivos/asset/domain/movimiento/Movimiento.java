package com.gestionactivos.asset.domain.movimiento;

import java.time.LocalDateTime;
import java.util.UUID;

public class Movimiento {
    private UUID idMovimiento;
    private UUID idActivo;
    private UUID idUsuario;
    private TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String observacion;

    public Movimiento() {
    }

    public Movimiento(UUID idMovimiento, UUID idActivo, UUID idUsuario, TipoMovimiento tipoMovimiento, LocalDateTime fechaMovimiento, String observacion) {
        this.idMovimiento = idMovimiento;
        this.idActivo = idActivo;
        this.idUsuario = idUsuario;
        this.tipoMovimiento = tipoMovimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.observacion = observacion;
    }

    public UUID getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(UUID idMovimiento) {
        this.idMovimiento = idMovimiento;
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

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
