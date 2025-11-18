package com.uade.tpo.marketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reportes_problema")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProblema {
    public static final String FOTO_DELIMITADOR = ";;";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre_apellido", nullable = false, length = 120)
    private String nombreApellido;

    @Column(name = "problematica", nullable = false, length = 200)
    private String problematica;

    @Column(name = "descripcion", nullable = false, length = 1500)
    private String descripcion;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(name = "fotos", length = 2000)
    private String fotos;
}
