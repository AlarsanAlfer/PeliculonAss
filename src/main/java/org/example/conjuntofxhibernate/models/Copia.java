package org.example.conjuntofxhibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase que representa las copias de las peliculas que tienen los usuarios
 */
@Data
@Entity
@Table(name="Copias")
public class Copia implements Serializable {
    @Column(name = "id_pelicula")
    private Long id_pelicula;
    @Column(name = "id_usuario")
    private Long id_usuario;
    private String titulo;
    private String estado;
    private String soporte;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Pelicula pelicula;


    public Copia(String titulo, String estado, String soporte, Long id) {
        this.titulo = titulo;
        this.estado = estado;
        this.soporte = soporte;
        this.id = id;
    }
    public Copia() {}

    public String toString() {
        return id + " - "+titulo + " - " + estado + " - " + soporte;
    }

}
