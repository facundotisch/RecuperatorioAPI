package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.controllers.reportes.ReporteProblemaRequest;
import com.uade.tpo.marketplace.entity.ReporteProblema;
import com.uade.tpo.marketplace.repository.ReporteProblemaRepository;
import com.uade.tpo.marketplace.service.ReporteProblemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteProblemaServiceImp implements ReporteProblemaService {

    private final ReporteProblemaRepository repository;

    @Override
    @Transactional
    public ReporteProblema registrar(@Valid ReporteProblemaRequest request) {
        ReporteProblema reporte = ReporteProblema.builder()
                .nombreApellido(request.getNombreApellido())
                .problematica(request.getProblematica())
                .descripcion(request.getDescripcion())
                .fotos(concatenarFotos(request.getFotos()))
                .creadoEn(LocalDateTime.now())
                .build();

        return repository.save(reporte);
    }

    @Override
    public ReporteProblema obtenerPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
    }

    private String concatenarFotos(List<String> fotos) {
        if (fotos == null || fotos.isEmpty()) {
            return null;
        }
        return String.join(ReporteProblema.FOTO_DELIMITADOR, fotos);
    }
}
