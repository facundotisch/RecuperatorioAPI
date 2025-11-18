package com.uade.tpo.marketplace.controllers.items;

import lombok.Data;

@Data
public class CarritoRequest {
    private String productoId;
    private int cantidad;
}
