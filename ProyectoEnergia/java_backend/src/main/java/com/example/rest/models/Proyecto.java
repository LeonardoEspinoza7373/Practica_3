package com.example.rest.models;

public class Proyecto {
    private int id; // Añadimos el campo id
    private String nombre;
    private String descripcion;
    private double inversion;
    private int tiempoVida;
    private String fechaInicio;
    private String fechaFin;
    private String[] inversionistas;
    private double electricidadGeneradaDiaria;
    
    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getInversion() {
        return inversion;
    }
    public void setInversion(double inversion) {
        this.inversion = inversion;
    }
    public int getTiempoVida() {
        return tiempoVida;
    }
    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }
    public String getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String[] getInversionistas() {
        return inversionistas;
    }
    public void setInversionistas(String[] inversionistas) {
        this.inversionistas = inversionistas;
    }
    public double getElectricidadGeneradaDiaria() {
        return electricidadGeneradaDiaria;
    }
    public void setElectricidadGeneradaDiaria(double electricidadGeneradaDiaria) {
        this.electricidadGeneradaDiaria = electricidadGeneradaDiaria;
    }

}
