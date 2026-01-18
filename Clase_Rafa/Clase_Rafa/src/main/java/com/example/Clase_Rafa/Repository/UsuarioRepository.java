package com.example.Clase_Rafa.Repository;

import com.example.Clase_Rafa.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsername(String username);


    Optional<Usuario> findUsuarioByUsername(String username);

    @Query(value = "Select * from usuario where username = :username",nativeQuery = true)
    Optional<Usuario> findAllByUsername( String  username);

    @Query(value = "SELECT * from usuario", nativeQuery = true)
    ArrayList<Usuario> findAll();

    long deleteByUsername(String username);


    Optional<Usuario> findUsuarioById(Long id);
}
