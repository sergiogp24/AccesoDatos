package com.example.Clase_Rafa.Service;

import ch.qos.logback.core.util.StringUtil;
import com.example.Clase_Rafa.Modelo.Usuario;
import com.example.Clase_Rafa.Repository.UsuarioRepository;
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
        if(!StringUtils.hasText(username)) throw new IllegalArgumentException("El username existe");
        return usuarioRepository.existsByUsername(username);
    }

    public boolean comprobarInicioSesion(Usuario usuarioraw){
        if(usuarioraw == null ) throw new IllegalArgumentException("El usuario viene vacio");
        Optional<Usuario> usuario1 = usuarioRepository.findUsuarioByUsername(usuarioraw.getUsername());
        if(usuario1.isEmpty()) throw  new IllegalStateException("El usuario no existe");

        if(!passwordEncoder.matches(usuarioraw.getContra(),usuario1.get().getContra())) throw new IllegalStateException("Las contrasenas no coinciden");
            return true;
        }
        public ArrayList<Usuario> mostrarUsuario(){
            return usuarioRepository.findAll();
        }

        public Optional<Usuario> buscarPorUsername( String  username){
        if(StringUtils.hasText(username)){
            Optional<Usuario> usuario = usuarioRepository.findAllByUsername(username);
            return usuario;
        }else{
            throw  new IllegalArgumentException("username no introducido");
        }
        }
    @Transactional
        public Usuario actualizarUsuario(Usuario usuario){
            if(usuario == null) throw new IllegalArgumentException("Usuario nulo");

            Optional<Usuario> existe = usuarioRepository.findUsuarioById(usuario.getId());

            if(existe.isEmpty()) throw new IllegalStateException("El usuario no existe");

            if (usuario.getContra().equals(existe.get().getContra())) {
                return usuarioRepository.save(usuario);
            }

            usuario.setContra(passwordEncoder.encode(usuario.getContra()));
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
            if(usuario.get().isActivo()){
                usuario.get().setActivo(false);
            } else {
                    usuario.get().setActivo(true);

            }
        }
        return  usuarioRepository.save(usuario.get());
    }


    }

