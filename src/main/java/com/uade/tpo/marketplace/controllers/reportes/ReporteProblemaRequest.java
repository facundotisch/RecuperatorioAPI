package com.uade.tpo.marketplace.controllers.reportes;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ReporteProblemaRequest {

    @NotBlank(message = "El nombre y apellido es obligatorio")
    @Size(max = 120, message = "El nombre y apellido no puede superar los 120 caracteres")
    private String nombreApellido;

    @NotBlank(message = "Debe indicar la problemática")
    @Size(max = 200, message = "La problemática no puede superar los 200 caracteres")
    private String problematica;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1500, message = "La descripción no puede superar los 1500 caracteres")
    private String descripcion;

    @NotEmpty(message = "Debe adjuntar al menos una imagen")
    @Size(max = 5, message = "Puede adjuntar hasta 5 imágenes por reporte")
    private List<@NotBlank(message = "La referencia de la imagen no puede estar vacía") String> fotos;

    @AssertTrue(message = "Solo se permiten imágenes jpg, jpeg, png, gif, bmp o webp")
    public boolean isFormatoImagenValido() {
        if (fotos == null) {
            return false;
        }
        return fotos.stream().allMatch(ReporteProblemaRequest::esFormatoValido);
    }

    private static boolean esFormatoValido(String valor) {
        if (valor == null) {
            return false;
        }
        String lower = valor.toLowerCase();
        return lower.startsWith("data:image/") ||
                lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg") ||
                lower.endsWith(".png") ||
                lower.endsWith(".gif") ||
                lower.endsWith(".bmp") ||
                lower.endsWith(".webp");
    }
}
