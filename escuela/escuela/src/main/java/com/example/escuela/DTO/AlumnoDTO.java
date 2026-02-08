package com.example.escuela.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDTO implements Serializable {
    private int id;
    private String nombre;
    private String apellidos;
    private String dni;

    private Date fechaNacimiento;
    private String observaciones;
    private boolean activo;

    public AlumnoDTO(String nombre, String apellidos, String dni,  Date fechaNacimiento, String observaciones, boolean activo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.observaciones = observaciones;
        this.activo = activo;
    }
}
