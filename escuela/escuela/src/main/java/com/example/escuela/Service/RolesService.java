package com.example.escuela.Service;

import com.example.escuela.Entity.Roles;
import com.example.escuela.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolesService {
    @Autowired
    RolesRepository rolesRepository;

    public Optional<Roles> buscarPorNombre(String rolNombre) {
        return rolesRepository.findByNombreRol(rolNombre);
    }
}
