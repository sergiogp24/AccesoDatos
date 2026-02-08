package com.example.escuela.Service;

import com.example.escuela.Entity.Alumno;
import com.example.escuela.Repository.AlumnoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    public List<Alumno> mostrarAlumnoPorId(int id){
        return alumnoRepository.findAllByUsuario_Id(id);
    }

    public List<Alumno> mostrarAlumno(){
        return alumnoRepository.findAll();
    }
    // Actualizar alumno
    @Transactional
    public Alumno actualizarAlumno(Alumno alumno) {
        if (alumno == null) throw new IllegalArgumentException("Alumno nulo");

        // Comprobación correcta: ¿existe el paciente por su id?
        if (!alumnoRepository.existsById(alumno.getId())) {
            throw new IllegalStateException("El alumno no existe");
        }

        return alumnoRepository.save(alumno);
    }

    // Eliminar alumno
    @Transactional
    public void eliminarAlumno(int id) {
        if (!alumnoRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        alumnoRepository.deleteById(id);
    }
}

