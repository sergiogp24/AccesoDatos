package com.example.escuela.Repository;

import com.example.escuela.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {


    Optional<Roles> findByNombreRol(String nombre);
}
