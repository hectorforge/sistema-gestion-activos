package com.gestionactivos.asset.domain.categoria;

import java.util.UUID;

public class Categoria {
    private UUID idCategoria;
    private String abreviaturaCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
    private String urlImgCategoria;
    private Boolean esActivo;
    private Boolean estaEliminado;

    public Categoria() {
    }

    public Categoria(UUID idCategoria, String abreviaturaCategoria, String nombreCategoria, String descripcionCategoria, String urlImgCategoria, Boolean esActivo, Boolean estaEliminado) {
        this.idCategoria = idCategoria;
        this.abreviaturaCategoria = abreviaturaCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
        this.urlImgCategoria = urlImgCategoria;
        this.esActivo = esActivo;
        this.estaEliminado = estaEliminado;
    }

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(UUID idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getAbreviaturaCategoria() {
        return abreviaturaCategoria;
    }

    public void setAbreviaturaCategoria(String abreviaturaCategoria) {
        this.abreviaturaCategoria = abreviaturaCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public String getUrlImgCategoria() {
        return urlImgCategoria;
    }

    public void setUrlImgCategoria(String urlImgCategoria) {
        this.urlImgCategoria = urlImgCategoria;
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
}
