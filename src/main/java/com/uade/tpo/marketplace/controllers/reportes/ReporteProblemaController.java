package com.uade.tpo.marketplace.controllers.reportes;

import com.uade.tpo.marketplace.service.ReporteProblemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reportes-problema")
@RequiredArgsConstructor
public class ReporteProblemaController {

    private final ReporteProblemaService service;

    @PostMapping
    @PreAuthorize("hasRole('COMPRADOR')")
    public ResponseEntity<Map<String, Object>> crearReporte(@Valid @RequestBody ReporteProblemaRequest request) {
        var reporte = service.registrar(request);
        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "El problema fue registrado correctamente",
                        "reporteId", reporte.getId()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ReporteProblemaResponse> obtenerReporte(@PathVariable UUID id) {
        return ResponseEntity.ok(
                ReporteProblemaResponse.fromEntity(service.obtenerPorId(id))
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<ReporteProblemaResponse>> listarReportes() {
        return ResponseEntity.ok(
                service.listarTodos().stream()
                        .map(ReporteProblemaResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
