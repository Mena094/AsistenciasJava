package com.ESFE.Asistencias.Utilidades;

import com.ESFE.Asistencias.Entidades.Docente;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class DocenteExportPDF {
    // Lista de docentes que se va a exportar en el PDF
    private List<Docente> listaDocentes;

    // Constructor para lista de docentes
    public DocenteExportPDF(List<Docente> listaDocentes){
        this.listaDocentes = listaDocentes;
    }

    // Método para configurar la cabecera de la tabla en el PDF
    private void setCabeceraTabla(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();

        // Configuración del estilo de la celda de cabecera
        Color colorPersonalizado = new Color(100, 150, 200); // Valores  para RGB
        celda.setBackgroundColor(colorPersonalizado);
        celda.setPadding(5);

        // Fuente para el texto en las celdas de cabecera
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.white);

        // Añadir celdas de cabecera para cada columna
        celda.setPhrase(new Phrase("Id",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Nombre",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Apellido",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Email",fuente));
        tabla.addCell(celda);


    }

    // Método para agregar los datos de los docentes a la tabla en el PDF
    private  void SetDatosTabla(PdfPTable tabla){
        for(Docente docente: listaDocentes){
            // Añadir una fila con los datos de cada docente
            tabla.addCell(String.valueOf(docente.getId()));
            tabla.addCell(docente.getNombre());
            tabla.addCell(docente.getApellido());
            tabla.addCell(docente.getEmail());
        }
    }

    // Método para exportar el documento PDF
    public void Exportar(HttpServletResponse response) throws IOException {
        // Crear un nuevo documento PDF con tamaño A4
        Document documento = new Document(PageSize.A4);

        // Obtener una instancia de PdfWriter para escribir en el documento
        PdfWriter.getInstance(documento,response.getOutputStream());

        // Abrir el documento para agregar contenido
        documento.open();

        try {
            // Cargar la imagen desde los recursos
            InputStream inputStream = getClass().getResourceAsStream("/static/dist/img/logo.png");
            if (inputStream != null) {
                Image imagen = Image.getInstance(ImageIO.read(inputStream), null);
                imagen.scaleToFit(100, 50); // Ajustar el tamaño de la imagen
                imagen.setAbsolutePosition(50, 750); // Posicionar la imagen en la esquina superior izquierda
                documento.add(imagen);
            } else {
                System.out.println("No se pudo cargar la imagen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Añadir un espacio en blanco para evitar que el título se superponga con la imagen
        documento.add(new Paragraph(" ")); // Espacio en blanco
        documento.add(new Paragraph(" ")); // Espacio en blanco adicional si es necesario

        // Fuente para el título del documento
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLACK);
        fuente.setSize(18);

        // Crear y agregar el título al documento
        Paragraph titulo = new Paragraph("Lista Docentes",fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        // Crear la tabla con 4 columnas
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);// Establecer el ancho de la tabla
        tabla.setSpacingBefore(15);// Espacio antes de la tabla
        tabla.setWidths(new float[] {1f,2f,2f,3.5f});// Ancho relativo de las columnas

        // Configurar y añadir las cabeceras y los datos a la tabla
        setCabeceraTabla(tabla);
        SetDatosTabla(tabla);

        // Añadir la tabla al documento
        documento.add(tabla);
        // Cerrar el documento
        documento.close();
    }
}
