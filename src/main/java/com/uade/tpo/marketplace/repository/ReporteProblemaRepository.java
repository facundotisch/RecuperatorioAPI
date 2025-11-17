package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.ReporteProblema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReporteProblemaRepository extends JpaRepository<ReporteProblema, UUID> {
}
