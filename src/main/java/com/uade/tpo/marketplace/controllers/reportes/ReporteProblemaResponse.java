package com.uade.tpo.marketplace.controllers.reportes;

import com.uade.tpo.marketplace.entity.ReporteProblema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class ReporteProblemaResponse {
    private UUID id;
    private String nombreApellido;
    private String problematica;
    private String descripcion;
    private LocalDateTime creadoEn;
    private List<String> fotos;

    public static ReporteProblemaResponse fromEntity(ReporteProblema reporte) {
        return ReporteProblemaResponse.builder()
                .id(reporte.getId())
                .nombreApellido(reporte.getNombreApellido())
                .problematica(reporte.getProblematica())
                .descripcion(reporte.getDescripcion())
                .creadoEn(reporte.getCreadoEn())
                .fotos(deserializarFotos(reporte.getFotos()))
                .build();
    }

    private static List<String> deserializarFotos(String valor) {
        if (valor == null || valor.isBlank()) {
            return List.of();
        }
        return Arrays.stream(valor.split(ReporteProblema.FOTO_DELIMITADOR))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
