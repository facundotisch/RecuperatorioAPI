package com.uade.tpo.marketplace.controllers.productos;

import lombok.Data;

@Data
public class ProductoAtributoRequest {
    private String atributoNombre; // "Color", "Talle", etc.
    private String valor;
}