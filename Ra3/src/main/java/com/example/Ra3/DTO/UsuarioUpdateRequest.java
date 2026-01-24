package com.example.Ra3.DTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequest {
    private String username;
    private String email;
    private String rol;     // admin/user
    private boolean estado;
    private Long departamentoId; // NUEVO
}
