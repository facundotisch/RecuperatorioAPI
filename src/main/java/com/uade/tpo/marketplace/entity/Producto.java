package com.uade.tpo.marketplace.entity;

import com.uade.tpo.marketplace.enums.Estados;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column
    private String foto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private int descuento;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estados estado = Estados.ACTIVO;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValorAtributo> datos = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime fechaHora;

}
