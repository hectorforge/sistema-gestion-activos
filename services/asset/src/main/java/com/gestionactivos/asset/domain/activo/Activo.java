package com.gestionactivos.asset.domain.activo;

import com.gestionactivos.asset.domain.activo.utils.EstadoActivo;
import com.gestionactivos.asset.domain.activo.utils.UbicacionFisica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Activo {

    private UUID idActivo;
    private String codigoInventario;

    private String nombreActivo;
    private String descripcion;
    private String urlImgActivo;
    private UUID categoriaId;
    private EstadoActivo estadoActual;
    private UbicacionFisica ubicacionFisica;
    private LocalDate fechaIngreso;
    private BigDecimal valorReferencial;
    private String proveedor;
    private Integer vidaUtilMeses;
    private String observaciones;

    // Campos de auditoría
    private Boolean esActivo;
    private Boolean estaEliminado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructors
    public Activo() {
    }

    public Activo(UUID idActivo, String codigoInventario, String nombreActivo, String descripcion, String urlImgActivo, UUID categoriaId, EstadoActivo estadoActual, UbicacionFisica ubicacionFisica, LocalDate fechaIngreso, BigDecimal valorReferencial, String proveedor, Integer vidaUtilMeses, String observaciones, Boolean esActivo, Boolean estaEliminado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
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
        this.estaEliminado = estaEliminado;
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

    public EstadoActivo getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoActivo estadoActual) {
        this.estadoActual = estadoActual;
    }

    public UbicacionFisica getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(UbicacionFisica ubicacionFisica) {
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

    public Boolean getEstaEliminado() {
        return estaEliminado;
    }

    public void setEstaEliminado(Boolean estaEliminado) {
        this.estaEliminado = estaEliminado;
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
}
