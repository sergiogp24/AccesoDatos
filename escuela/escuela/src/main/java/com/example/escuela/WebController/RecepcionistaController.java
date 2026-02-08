package com.example.escuela.WebController;

import com.example.escuela.DTO.AlumnoDTO;
import com.example.escuela.DTO.UsuarioDTO;
import com.example.escuela.Entity.Alumno;
import com.example.escuela.Service.AlumnoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recepcionista" )
public class RecepcionistaController {
    @Autowired
    AlumnoService alumnoService;
    @ResponseBody
    @RequestMapping("/veralumnos")

    public ResponseEntity<?> mostrarAlumnos(HttpSession session) {
        UsuarioDTO usuarioSesion = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (usuarioSesion == null || !"recepcion".equals(usuarioSesion.getRol())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            List<Alumno> lista = alumnoService.mostrarAlumno();
            List<AlumnoDTO> lista2 = new ArrayList<>();
            for(Alumno alumnos : lista){
                AlumnoDTO alumnodto = new AlumnoDTO(alumnos.getId(),alumnos.getNombre(), alumnos.getApellidos(), alumnos.getDni(), alumnos.getFechaNacimiento(), alumnos.getObservaciones(),alumnos.isActivo());
                lista2.add(alumnodto);
            }
            return ResponseEntity.ok(lista2);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No hay alumnos que mostrar");

        }
    }
}
