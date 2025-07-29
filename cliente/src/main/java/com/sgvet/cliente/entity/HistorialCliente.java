package com.sgvet.cliente.entity;

import java.time.LocalDateTime;

public class HistorialCliente {

    private Integer id;
    private Integer idCliente;
    private Integer idMascota;
    private String tipoConsulta;
    private String descripcion;
    private String diagnostico;
    private String tratamiento;
    private LocalDateTime fechaConsulta;
    private String veterinario;
    private Double costo;
    private String observacion;

    public HistorialCliente() {
    }

    public HistorialCliente(Integer id, Integer idCliente, Integer idMascota, String tipoConsulta,
                            String descripcion, String diagnostico, String tratamiento,
                            LocalDateTime fechaConsulta, String veterinario, Double costo, String observacion) {
        this.id = id;
        this.idCliente = idCliente;
        this.idMascota = idMascota;
        this.tipoConsulta = tipoConsulta;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fechaConsulta = fechaConsulta;
        this.veterinario = veterinario;
        this.costo = costo;
        this.observacion = observacion;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return String.format("HistorialCliente{id=%d, idCliente=%d, idMascota=%d, tipoConsulta='%s', " +
                        "descripcion='%s', diagnostico='%s', tratamiento='%s', " +
                        "fechaConsulta=%s, veterinario='%s', costo=%.2f, observacion='%s'}",
                id, idCliente, idMascota, tipoConsulta, descripcion, diagnostico,
                tratamiento, fechaConsulta, veterinario, costo, observacion);
    }
}