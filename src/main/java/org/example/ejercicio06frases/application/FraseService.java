package org.example.ejercicio06frases.application;

import org.example.ejercicio06frases.domain.entities.Frase;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface FraseService {
    public List<Frase> findAll();
    public Frase findById(Long id);
    public Frase save(Frase frase);
    public void deleteById(Long id);

    public ByteArrayInputStream reportePDF(List<Frase> frases);

    Frase obtenerFraseAleatoria();
    void enviarFrasePorCorreo(String email);
}
