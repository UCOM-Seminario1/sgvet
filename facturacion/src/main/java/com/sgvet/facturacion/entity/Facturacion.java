package com.sgvet.facturacion.entity;

public class Facturacion {

    private Integer id;
    private String nombre;
    private String razonSocial;
    private Integer cantidad;
    private String importe;
    private Integer iva;
    private String total;
    private String descripcion;

    public Facturacion() {
    }

    public Facturacion(Integer id, String nombre, String razonSocial, Integer cantidad, String importe, Integer iva, String total, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.cantidad = cantidad;
        this.importe = importe;
        this.iva = iva;
        this.total = total;
        this.descripcion = descripcion;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Facturacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", cantidad=" + cantidad +
                ", importe='" + importe + '\'' +
                ", iva=" + iva +
                ", total='" + total + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
