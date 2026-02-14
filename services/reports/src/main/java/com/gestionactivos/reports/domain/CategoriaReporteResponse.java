package com.gestionactivos.reports.domain;

public class CategoriaReporteResponse {
    private String idCategoria;
    private String nombreCategoria;
    private String abreviaturaCategoria;
    private String descripcionCategoria;
    private String urlImgCategoria;
    private String esActivo;
    private String estaEliminad;

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getAbreviaturaCategoria() {
        return abreviaturaCategoria;
    }

    public void setAbreviaturaCategoria(String abreviaturaCategoria) {
        this.abreviaturaCategoria = abreviaturaCategoria;
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

    public String getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public String getEstaEliminad() {
        return estaEliminad;
    }

    public void setEstaEliminad(String estaEliminad) {
        this.estaEliminad = estaEliminad;
    }
}
