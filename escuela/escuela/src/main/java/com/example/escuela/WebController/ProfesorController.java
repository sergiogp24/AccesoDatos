package com.example.escuela.WebController;

import com.example.escuela.DTO.AlumnoDTO;
import com.example.escuela.DTO.UsuarioDTO;
import com.example.escuela.Entity.Alumno;
import com.example.escuela.Repository.AlumnoRepository;
import com.example.escuela.Repository.RolesRepository;
import com.example.escuela.Service.AlumnoService;
import com.example.escuela.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profesor" )
public class ProfesorController {
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    private UsuarioService usuarioService;

    @ResponseBody
    @RequestMapping("/veralumnos")

    public ResponseEntity<?> mostrarAlumnos(HttpSession session) {
        UsuarioDTO usuarioSesion = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesion == null || !"profesor".equals(usuarioSesion.getRol())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            List<Alumno> lista = alumnoService.mostrarAlumnoPorId(usuarioSesion.getId());
            List<AlumnoDTO> lista2 = new ArrayList<>();
            for(Alumno alumno : lista){
                AlumnoDTO alumnodto = new AlumnoDTO(alumno.getId(),alumno.getNombre(), alumno.getApellidos(), alumno.getDni(),  alumno.getFechaNacimiento(), alumno.getObservaciones(),alumno.isActivo());
                lista2.add(alumnodto);
            }
            return ResponseEntity.ok(lista2);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No hay alumnos que mostrar");

        }
    }
    @ResponseBody
    @DeleteMapping("/eliminaralumno/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable int id, HttpSession session){
        UsuarioDTO usuarioSesion = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesion == null || !"profesor".equals(usuarioSesion.getRol())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

       alumnoService.eliminarAlumno(id);
        return ResponseEntity.ok("Usuario eliminado: " + id);
    }

    @ResponseBody
    @PutMapping("/editaralumno")
    public ResponseEntity<?> actualizarAlumno
(@RequestBody AlumnoDTO datosAlumno, HttpSession session){
        UsuarioDTO usuarioSesion = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesion == null || !"profesor".equals(usuarioSesion.getRol())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Alumno> misAlumnos = alumnoService.mostrarAlumnoPorId(usuarioSesion.getId());
        Alumno alumno = misAlumnos.stream()
                .filter(p -> p.getId() == datosAlumno.getId())
                .findFirst()
                .orElse(null);

        if (alumno == null) {
            return ResponseEntity.badRequest().body("Alumno no existe o no pertenece al profesor");
        }

        // Actualizamos los campos del Alumno

        alumno.setNombre(datosAlumno.getNombre());
        alumno.setApellidos(datosAlumno.getApellidos());
        alumno.setDni(datosAlumno.getDni());
        alumno.setFechaNacimiento(datosAlumno.getFechaNacimiento());
        alumno.setObservaciones(datosAlumno.getObservaciones());
        alumno.setActivo(datosAlumno.isActivo());

        // Guardamos cambios
        Alumno alumnoGuardado = alumnoService.actualizarAlumno(alumno);

        return ResponseEntity.ok(alumnoGuardado);
    }

}

