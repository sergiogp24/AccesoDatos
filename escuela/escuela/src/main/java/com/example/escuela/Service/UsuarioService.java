package com.example.escuela.Service;

import com.example.escuela.DTO.LoginDTO;
import com.example.escuela.Entity.Usuario;
import com.example.escuela.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean comprobarInicioSesion(LoginDTO logindto){
        if (logindto == null) throw new IllegalArgumentException("El usuario viene vacio al service");
        Optional<Usuario> usuariobd = usuarioRepository.findUsuarioByUsername(logindto.getUsername());
        if(usuariobd.isEmpty()) throw new IllegalStateException("El usuario no existe");

        if(!passwordEncoder.matches(logindto.getPassword(),usuariobd.get().getPasswordHash())) throw new IllegalStateException("Las contraseñas no coinciden");
        return true;

    }

    public Optional<Usuario> obtenerUsuarioPorUsername(String username){
        return usuarioRepository.findUsuarioByUsername(username);
    }

// Actualizar usuario
    @Transactional
    public Usuario actualizarUsuario(Usuario usuario){
        if (usuario == null) throw new IllegalArgumentException("Usuario nulo");
        Optional<Usuario> existe = usuarioRepository.findUsuarioById(usuario.getId());
        if (existe.isEmpty()) throw new IllegalStateException("El usuario no existe");
        return usuarioRepository.save(usuario);
    }
    // Eliminar Usuario
    @Transactional
    public void eliminarUsuario(int id){
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    //Mostrar Usuarios
    public List<Usuario> mostrarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id);
    }


}
