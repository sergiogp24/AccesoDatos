package com.example.Clase_Rafa.Controller;

import com.example.Clase_Rafa.Modelo.Usuario;
import com.example.Clase_Rafa.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

@Component
public class UsuarioController implements CommandLineRunner {
    public static Scanner sc = new Scanner(System.in);
    @Autowired
    UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        while (true) {

            System.out.println("\n--- MENU USUARIO ---");
            System.out.println("1. Test de conexión");
            System.out.println("2. Crear usuario");
            System.out.println("3. Listar usuarios ");
            System.out.println("4. Buscar por username");
            System.out.println("5. Actualizar Usuario");
            System.out.println("6. Desactivar usuario (borrado lógico)");
            System.out.println("7. Eliminar usuario (borrado físico)");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    iniciarSesion();
                    break;
                case "2":
                    darAltaUsuario();
                    break;
                case "3":
                    listarUsuario();
                    break;
                case "4":
                    buscarUsername();
                    break;
                case "5":
                    actualizarUsuario();
                    break;
                case "6":
                    borradoLogico();
                    break;
                case "7":
                    eliminarUsuario();
                    break;
                case "0":
                    System.out.println("Vuelve pronto.");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }


    public boolean iniciarSesion() {
        boolean logeado = false;
        while (!logeado) {
            System.out.println(" Dime un username");
            String username = sc.nextLine();
            try {
                if (!usuarioService.comprobarUsername(username))
                    throw new IllegalStateException("El usuario no existe");
                System.out.println("Dime la contrasena: ");
                String password = sc.nextLine();
                if (usuarioService.comprobarInicioSesion(new Usuario(username, password))) {
                    logeado = true;
                }

            } catch (IllegalArgumentException  | IllegalStateException e) {
                System.out.println(e.getMessage());

            }

        }
        return logeado ;
    }

    public void darAltaUsuario() {
        try {
            System.out.println("Dime el username:");
            String username = sc.nextLine();
            if (usuarioService.comprobarUsername(username)) throw new IllegalStateException("El usuario ya existe");
            System.out.println("Introduce tu contrasena: ");
            String password = sc.nextLine();
            Usuario usuario = new Usuario(username, password);
            Usuario usuariocreado = usuarioService.crearUsuarioo(usuario);
            if (usuariocreado == null) throw new IllegalStateException("El usuario no se ha creado correctamente");
            System.out.println("El usuario se ha creado correctamente con el id: " + usuariocreado.getId());


        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarUsuario(){
        ArrayList<Usuario> lista;
        try{
            lista = usuarioService.mostrarUsuario();
            if(lista.isEmpty()){
                throw new IllegalStateException("La lista esta vacia");
            }
            System.out.println("LISTA DE USUARIOS REGISTRADOS");
            for(Usuario usuario : lista){
                String linea = " | Nombre de usuario: " + usuario.getUsername()  + " | Activo: " + (usuario.isActivo() ? "Está activo" : "No está activo");
                System.out.println(linea);
            }

        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }
    public void buscarUsername(){
        try{
            System.out.println("Dime que usuario quieres buscar:");
            String username = sc.nextLine();
            Optional<Usuario> usuario = usuarioService.buscarPorUsername(username);
            if(usuario.isEmpty()){
                throw  new IllegalStateException("El usuario no existe");
            }
            String linea =  " | Nombre de usuario: " + usuario.get().getUsername() +  " | Estado: " + (usuario.get().isActivo() ? "Está activo" : "No está activo");
            System.out.println(linea);
        }catch (IllegalStateException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public void actualizarUsuario(){
        System.out.println("Introduce el nickname: ");
        String username = sc.nextLine();
        try{
            if(!usuarioService.existeUsername(username)){
                throw new IllegalStateException("El usuario no existe");
            }
            Optional <Usuario> usuario = usuarioService.buscarPorUsername(username);
            String linea = " Nombre de usuario: " + usuario.get().getUsername() + " | Estado: " + (usuario.get().isActivo() ? "Está activo" : "No está activo");
            System.out.println(linea);
            System.out.println("Introduce el nuevo nickname: ");
            String usuarionuevo = sc.nextLine();
            System.out.println("Introduce la nueva contrasena ");
            String contra = sc.nextLine();
            System.out.println("Introduce el nuevo Estado");
            boolean estado = sc.hasNextBoolean();
            sc.nextLine();
            usuario.get().setUsername(usuarionuevo);
            usuario.get().setContra(contra);
            usuario.get().setActivo(estado);
           Usuario usuarioactualizado = usuarioService.actualizarUsuario(usuario.get());

            String linea2 =  " | Nombre de usuario: " + usuarioactualizado.getUsername() + " | Estado: " + (usuarioactualizado.isActivo() ? "Está activo" : "No está activo");
            System.out.println(linea2);

        }catch (IllegalStateException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void eliminarUsuario(){
        System.out.println("Primero introduce el nickname");
        String username = sc.nextLine();
        try {
            if(!usuarioService.existeUsername(username)){
                throw new IllegalStateException("El usuario no existe");
            }
            if (usuarioService.eliminarUsuario(username)) {
                System.out.println("Usuario Eliminado correctamente");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void borradoLogico() {

        System.out.println("Primero introduce el username");
        String username = sc.nextLine();
        try {
            if(!usuarioService.existeUsername(username)){
                throw new IllegalStateException("El usuario no existe");
            }

            Usuario usuario = usuarioService.desactivarUsuario(username);
            if (usuario == null) {
                throw new IllegalStateException("Problemas al cambiar el estado");
            }

            System.out.println("Estado cambiado a desactivado");
            String linea =  " | Nombre de usuario: " + usuario.getUsername() +  " | Estado: " + (usuario.isActivo() ? "Está activo" : "No está activo");
            System.out.println(linea);


        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
