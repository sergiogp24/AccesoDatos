package com.example.Ra3.Service;

import com.example.Ra3.Modelo.Usuario;
import com.example.Ra3.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;//Necesario para hashear la contra

    public Usuario crearUsuarioo(Usuario usuario){
        if(usuario == null ) throw new IllegalStateException("El usuario viene vacio");
        if(usuarioRepository.existsByUsername(usuario.getUsername())) throw new IllegalStateException("El usuario ya existe");

        usuario.setContra(passwordEncoder.encode(usuario.getContra()));
        return usuarioRepository.save(usuario); // el save sirve para insertar y actualizar
    }

    public boolean comprobarUsername(String username){
        if(!StringUtils.hasText(username)) throw new IllegalArgumentException("El username no introducido");
        return usuarioRepository.existsByUsername(username);
    }

    public boolean comprobarInicioSesion(Usuario usuarioraw){
        if (usuarioraw == null) throw new IllegalArgumentException("El usuario viene vacio al service");
        Optional<Usuario> usuariobd = usuarioRepository.findUsuarioByUsername(usuarioraw.getUsername());
        if(usuariobd.isEmpty()) throw new IllegalStateException("El usuario no existe");

        if(!passwordEncoder.matches(usuarioraw.getContra(),usuariobd.get().getContra())) throw new IllegalStateException("Las contraseñas no coinciden");
        return true;
    }

    public ArrayList<Usuario> mostrarUsuario(){
        return usuarioRepository.findAllBy();
    }

    public Optional<Usuario> buscarPorUsername(String username){
        if(StringUtils.hasText(username)){
            Optional<Usuario> usuario = usuarioRepository.findAllByUsername(username);
            return usuario;
        }else{
            throw  new IllegalArgumentException("username no introducido");
        }
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findUsuarioById(id);
    }

    @Transactional
    public Usuario actualizarUsuario(Usuario usuario){
        if(usuario == null) throw new IllegalArgumentException("Usuario nulo");

        Optional<Usuario> existe = usuarioRepository.findUsuarioById(usuario.getId());
        if(existe.isEmpty()) throw new IllegalStateException("El usuario no existe");

        if (!usuario.getContra().equals(existe.get().getContra())) {
            usuario.setContra(passwordEncoder.encode(usuario.getContra()));
        }
        return usuarioRepository.save(usuario);
    }

    public boolean existeUsername(String username){
        return usuarioRepository.existsByUsername(username);
    }

    @Transactional
    public boolean eliminarUsuario(String username){
        return usuarioRepository.deleteByUsername(username) > 0;
    }

    public Usuario desactivarUsuario(String username){
        Optional <Usuario> usuario = Optional.empty();
        if(StringUtils.hasText(username)){
            if(!existeUsername(username)){
                throw new IllegalStateException("El username no existe");
            }
            usuario = buscarPorUsername(username);
            usuario.get().setActivo(!usuario.get().isActivo());
        }
        return  usuarioRepository.save(usuario.get());
    }

    @Transactional
    public Usuario cambiarPassword(String username, String passwordActual, String passwordNueva) {
        if (username== null || !StringUtils.hasText(passwordActual) || !StringUtils.hasText(passwordNueva)) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }
        Usuario usuario = usuarioRepository.findUsuarioByUsername(username)
                .orElseThrow(() -> new IllegalStateException("El usuario no existe"));

        if (!usuario.isActivo()) {
            throw new IllegalStateException("El usuario está bloqueado. No es posible cambiar la contraseña.");
        }

        if (!passwordEncoder.matches(passwordActual, usuario.getContra())) {
            throw new IllegalStateException("La contraseña actual no es correcta");
        }

        usuario.setContra(passwordEncoder.encode(passwordNueva));
        return usuarioRepository.save(usuario);
    }

    public boolean validarContrasena(String username, String password){
        Optional<Usuario> usuario = usuarioRepository.findUsuarioByUsername(username);
        return usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getContra());
    }
}
