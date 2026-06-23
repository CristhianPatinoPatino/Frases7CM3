package org.example.ejercicio06frases.infrastructure;


import org.example.ejercicio06frases.application.FraseService;
import org.example.ejercicio06frases.domain.entities.Frase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/api/v1/frases")
public class FraseController {
    @Autowired
    private FraseService servicio;

    @GetMapping("/frase")
    public List<Frase> findAll(){
        return  servicio.findAll();
    }

    @GetMapping("/frase/{id}")
    public Frase findById(@PathVariable Long id){
        return servicio.findById(id);
    }

    @PostMapping("/frase")
    @ResponseStatus(HttpStatus.CREATED)
    public Frase save(@RequestBody Frase frase){
        return servicio.save(frase);
    }

    @PutMapping("/frase/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Frase update(@PathVariable Long id, @RequestBody Frase frase){
        Frase f = servicio.findById(id);
        f.setTexto(frase.getTexto());
        f.setAutor(frase.getAutor());
        return servicio.save(f);
    }

    @DeleteMapping("/frase/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        servicio.deleteById(id);
    }

    @GetMapping("/frase/pdf")
    public ResponseEntity<InputStreamResource> getPdf(){
        List<Frase> frases = (List<Frase>) servicio.findAll();
        ByteArrayInputStream bais = servicio.reportePDF(frases);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=frase.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }

    @PostMapping("/frase/enviar")
    public ResponseEntity<String> enviarCorreo(@RequestParam String email){
        servicio.enviarFrasePorCorreo(email);
        return ResponseEntity.ok("Correo enviado correctamente");
    }
}
