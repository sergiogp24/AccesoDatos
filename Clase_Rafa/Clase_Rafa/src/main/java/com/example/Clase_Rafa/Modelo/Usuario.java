package com.example.Clase_Rafa.Modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    @Column(name= "username", unique = true, length = 100)
    private String username;

    @Column(name="password", nullable = false)
    private String contra;

    @Column(name="activo")
    private boolean activo = true;

    public Usuario(String username, String contra) {
        this.username = username;
        this.contra = contra;
    }
}
