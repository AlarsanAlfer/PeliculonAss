package org.example.conjuntofxhibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase que representa las diferentes peliculas.
 */
@Data
@Entity
@Table(name = "Peliculas")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String genero;
    private Integer anio;
    private String descripcion;
    private String director;
    private String portada;



    public Pelicula(String titulo, Integer anio, String genero, String descripcion, String director, String portada) {
        this.titulo = titulo;
        this.anio = anio;
        this.genero = genero;
        this.descripcion = descripcion;
        this.director = director;
        this.portada = portada;
    }

    public Pelicula() {}
}
