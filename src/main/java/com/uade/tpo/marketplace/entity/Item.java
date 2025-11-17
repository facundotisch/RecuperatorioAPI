package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false) // relación con Compra
    @JsonBackReference
    private Compra compra;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private LocalDateTime fechaHora  = LocalDateTime.now();
}
