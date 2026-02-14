package com.gestionactivos.reports.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ActivoReporteResponse {
    private UUID idActivo;
    private String codigoInventario;
    private String nombreActivo;
    private String descripcion;
    private String urlImgActivo;
    private UUID categoriaId;
    private String estadoActual;
    private String ubicacionFisica;
    private LocalDate fechaIngreso;
    private BigDecimal valorReferencial;
    private String proveedor;
    private Integer vidaUtilMeses;
    private String observaciones;
    private Boolean esActivo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ActivoReporteResponse() {
    }

    public ActivoReporteResponse(UUID idActivo, String codigoInventario, String nombreActivo, String descripcion, String urlImgActivo, UUID categoriaId, String estadoActual, String ubicacionFisica, LocalDate fechaIngreso, BigDecimal valorReferencial, String proveedor, Integer vidaUtilMeses, String observaciones, Boolean esActivo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idActivo = idActivo;
        this.codigoInventario = codigoInventario;
        this.nombreActivo = nombreActivo;
        this.descripcion = descripcion;
        this.urlImgActivo = urlImgActivo;
        this.categoriaId = categoriaId;
        this.estadoActual = estadoActual;
        this.ubicacionFisica = ubicacionFisica;
        this.fechaIngreso = fechaIngreso;
        this.valorReferencial = valorReferencial;
        this.proveedor = proveedor;
        this.vidaUtilMeses = vidaUtilMeses;
        this.observaciones = observaciones;
        this.esActivo = esActivo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public UUID getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(UUID idActivo) {
        this.idActivo = idActivo;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getNombreActivo() {
        return nombreActivo;
    }

    public void setNombreActivo(String nombreActivo) {
        this.nombreActivo = nombreActivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImgActivo() {
        return urlImgActivo;
    }

    public void setUrlImgActivo(String urlImgActivo) {
        this.urlImgActivo = urlImgActivo;
    }

    public UUID getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(UUID categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getValorReferencial() {
        return valorReferencial;
    }

    public void setValorReferencial(BigDecimal valorReferencial) {
        this.valorReferencial = valorReferencial;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getVidaUtilMeses() {
        return vidaUtilMeses;
    }

    public void setVidaUtilMeses(Integer vidaUtilMeses) {
        this.vidaUtilMeses = vidaUtilMeses;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivoReporteResponse that = (ActivoReporteResponse) o;
        return Objects.equals(idActivo, that.idActivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idActivo);
    }

    @Override
    public String toString() {
        return "ActivoReporteResponse{" +
                "idActivo=" + idActivo +
                ", codigoInventario='" + codigoInventario + '\'' +
                ", nombreActivo='" + nombreActivo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", urlImgActivo='" + urlImgActivo + '\'' +
                ", categoriaId=" + categoriaId +
                ", estadoActual='" + estadoActual + '\'' +
                ", ubicacionFisica='" + ubicacionFisica + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", valorReferencial=" + valorReferencial +
                ", proveedor='" + proveedor + '\'' +
                ", vidaUtilMeses=" + vidaUtilMeses +
                ", observaciones='" + observaciones + '\'' +
                ", esActivo=" + esActivo +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
