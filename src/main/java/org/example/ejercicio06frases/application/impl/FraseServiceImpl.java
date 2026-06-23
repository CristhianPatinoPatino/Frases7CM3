package org.example.ejercicio06frases.application.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.ejercicio06frases.application.FraseService;
import org.example.ejercicio06frases.domain.entities.Frase;
import org.example.ejercicio06frases.domain.repository.FraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FraseServiceImpl implements FraseService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FraseRepository dao;

    @Override
    @Transactional(readOnly = true)
    public List<Frase> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Frase findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Frase save(Frase frase) {
        return dao.save(frase);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public ByteArrayInputStream reportePDF(List<Frase> frases) {
        Document document = new Document();
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, salida);
            document.open();
            Font tipoLetra = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph parrafo = new Paragraph("Lista de Frases", tipoLetra);
            document.add(parrafo);
            document.add(Chunk.NEWLINE);

            Font tipoLetraTexto = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            PdfPTable tabla = new PdfPTable(3);
            Stream.of("Id", "Texto", "Autor").forEach(encabezados -> {
                PdfPCell encabezadosTabla = new PdfPCell();
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.RED);
                encabezadosTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
                encabezadosTabla.setVerticalAlignment(Element.ALIGN_CENTER);
                encabezadosTabla.setBorder(1);
                encabezadosTabla.setPhrase(new Phrase(encabezados, headerFont));
                tabla.addCell(encabezadosTabla);
            });

            for (Frase frase : frases) {
                PdfPCell celdaIdFrase = new PdfPCell(new Phrase(String.valueOf(frase.getId()), tipoLetraTexto));
                celdaIdFrase.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaIdFrase.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaIdFrase.setBorder(1);
                celdaIdFrase.setPadding(2);
                tabla.addCell(celdaIdFrase);

                PdfPCell celdaIdTexto = new PdfPCell(new Phrase(frase.getTexto(), tipoLetraTexto));
                celdaIdTexto.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaIdTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaIdTexto.setBorder(1);
                celdaIdTexto.setPadding(2);
                tabla.addCell(celdaIdTexto);

                PdfPCell celdaIdAutor = new PdfPCell(new Phrase(frase.getAutor(), tipoLetraTexto));
                celdaIdAutor.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaIdAutor.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaIdAutor.setBorder(1);
                celdaIdAutor.setPadding(2);
                tabla.addCell(celdaIdAutor);
            }
            document.add(tabla);
            document.close();

        }catch (DocumentException de){
            System.err.println(de.getMessage());
        }
        return new ByteArrayInputStream( salida.toByteArray() );
    }

    @Override
    public Frase obtenerFraseAleatoria() {
        return dao.obtenerFraseAleatoria();
    }

    @Override
    public void enviarFrasePorCorreo(String email) {
        Frase frase = obtenerFraseAleatoria();

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email);
        mensaje.setSubject("Frase Aleatoria");
        mensaje.setText("Frase: " + frase.getTexto() + "\nAutor: " + frase.getAutor());

        mailSender.send(mensaje);
    }
}
