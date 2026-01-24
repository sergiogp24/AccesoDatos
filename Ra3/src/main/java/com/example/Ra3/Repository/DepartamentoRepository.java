package com.example.Ra3.Repository;

import com.example.Ra3.Modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByNombre(String nombre);
    Optional<Departamento> findByNombre(String nombre);
}
