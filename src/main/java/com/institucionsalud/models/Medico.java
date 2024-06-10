package com.institucionsalud.models;

public class Medico {
    private int id;
    private String nombre;
    private int especialidadId;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEspecialidadId() { return especialidadId; }
    public void setEspecialidadId(int especialidadId) { this.especialidadId = especialidadId; }
}