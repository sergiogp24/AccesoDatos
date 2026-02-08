package com.example.escuela.Repository;

import com.example.escuela.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


    Optional<Usuario> findUsuarioByUsername(String username);

    Optional<Usuario> findUsuarioById(int id);

}
