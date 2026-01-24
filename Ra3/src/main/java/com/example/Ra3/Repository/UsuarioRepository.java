package com.example.Ra3.Repository;

import com.example.Ra3.Modelo.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsername(String username);
    Optional<Usuario> findUsuarioByUsername(String username);
    Optional<Usuario> findUsuarioById(Long id);

    // Para compatibilidad con el servicio actual
    Optional<Usuario> findAllByUsername(String username);
    ArrayList<Usuario> findAllBy();

    int deleteByUsername(String username);

    // Ejercicio 3: paginación (ya disponible por JpaRepository)
    Page<Usuario> findAll(Pageable pageable);
    boolean existsByEmail(String email);
    // NUEVO: cuantos usuarios referencian un departamento (para borrado seguro)
    long countByDepartamento_Id(Long departamentoId);

}
