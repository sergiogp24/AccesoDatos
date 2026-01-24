package com.example.Ra3.DTO;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioViewDTO {
    private Long id;
    private String username;
    private String email;
    private boolean estado;
    private String rol;
    private LocalDateTime fechaCreacion;

    // Campos extra para departamento
    private Long departamentoId;
    private String departamentoNombre;
}
