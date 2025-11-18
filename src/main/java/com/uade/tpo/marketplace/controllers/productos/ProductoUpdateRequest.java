package com.uade.tpo.marketplace.controllers.productos;

import lombok.Data;

@Data
public class ProductoUpdateRequest {
    private String nombre;
    private String descripcion;
    private Float valor;
    private Integer cantidad;
    private Integer descuento;
}