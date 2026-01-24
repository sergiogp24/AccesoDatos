package com.example.Ra3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private boolean estado;
    private String rol;
    private LocalDateTime fechaCreacion;

    // Constructor de conveniencia
    public UsuarioDTO(Long id, String username, String email, LocalDateTime fechaCreacion) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
    }
}