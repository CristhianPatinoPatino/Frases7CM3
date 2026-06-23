package org.example.ejercicio06frases.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor()
@AllArgsConstructor()
@Data
@Builder
@Entity
@Table(name = "frase")
public class Frase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "texto", length = 500, nullable = false)
    private String texto;
    @Column(name = "autor", length = 100, nullable = false)
    private String autor;
}
