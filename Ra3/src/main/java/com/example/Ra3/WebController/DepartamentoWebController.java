package com.example.Ra3.WebController;
import com.example.Ra3.DTO.DepartamentoDTO;
import com.example.Ra3.DTO.UsuarioDTO;
import com.example.Ra3.Modelo.Departamento;
import com.example.Ra3.Service.DepartamentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoWebController {

    @Autowired
    private DepartamentoService departamentoService;

    // Página de gestión (solo admin por comprobación de sesión)
    @GetMapping("")
    public String paginaDepartamentos(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (u == null) return "redirect:/killSession";
        if (!"admin".equalsIgnoreCase(u.getRol())) return "redirect:/index.html";
        return "departamentos";
    }

    @ResponseBody
    @GetMapping("/lista")
    public ResponseEntity<List<DepartamentoDTO>> lista(HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (u == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!"admin".equalsIgnoreCase(u.getRol())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<DepartamentoDTO> data = departamentoService.listar().stream()
                .map(d -> new DepartamentoDTO(d.getId(), d.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(data);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<DepartamentoDTO> crear(@RequestBody DepartamentoDTO req, HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (u == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!"admin".equalsIgnoreCase(u.getRol())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Departamento created = departamentoService.crear(req.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DepartamentoDTO(created.getId(), created.getNombre()));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> actualizar(@PathVariable Long id, @RequestBody DepartamentoDTO req, HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (u == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!"admin".equalsIgnoreCase(u.getRol())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Departamento updated = departamentoService.actualizar(id, req.getNombre());
        return ResponseEntity.ok(new DepartamentoDTO(updated.getId(), updated.getNombre()));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, HttpSession session) {
        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuarioLogeado");
        if (u == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!"admin".equalsIgnoreCase(u.getRol())) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            departamentoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // en uso por usuarios
        }
    }
}