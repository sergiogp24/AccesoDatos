package com.example.escuela.Repository;

import com.example.escuela.Entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    List<Alumno> findAllByUsuario_Id(int usuarioId);
}
