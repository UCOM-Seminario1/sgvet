package com.sgvet.facturacion.entity;

public class FiltroBusquedaFactura {
    private Integer id;
    private String nombre;
    private String razonSocial;

    public FiltroBusquedaFactura() {}

    public FiltroBusquedaFactura(Integer id, String nombre, String razonSocial) {
        this.id = id;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
