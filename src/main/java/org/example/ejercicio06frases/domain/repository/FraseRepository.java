package org.example.ejercicio06frases.domain.repository;

import org.example.ejercicio06frases.domain.entities.Frase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FraseRepository extends JpaRepository<Frase, Long> {
    @Query(value = "SELECT * FROM Frase ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Frase obtenerFraseAleatoria();
}
