package com.institucionsalud.models;

import java.util.Date;

public class Ingreso {
    private int id;
    private int pacienteId;
    private int medicoId;
    private int especialidadId;
    private int diagnosticoId;
    private Date fechaIngreso;
    private Date fechaAlta;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }
    public int getMedicoId() { return medicoId; }
    public void setMedicoId(int medicoId) { this.medicoId = medicoId; }
    public int getEspecialidadId() { return especialidadId; }
    public void setEspecialidadId(int especialidadId) { this.especialidadId = especialidadId; }
    public int getDiagnosticoId() { return diagnosticoId; }
    public void setDiagnosticoId(int diagnosticoId) { this.diagnosticoId = diagnosticoId; }
    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Date getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(Date fechaAlta) { this.fechaAlta = fechaAlta; }
}