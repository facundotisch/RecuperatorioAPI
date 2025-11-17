package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controllers.reportes.ReporteProblemaRequest;
import com.uade.tpo.marketplace.entity.ReporteProblema;

import java.util.UUID;

public interface ReporteProblemaService {

    ReporteProblema registrar(ReporteProblemaRequest request);

    ReporteProblema obtenerPorId(UUID id);
}
