package com.uade.tpo.marketplace.controllers.items;

import com.uade.tpo.marketplace.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String productoNombre;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public static ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getProducto().getValor(),
                item.getProducto().getValor() * item.getCantidad()
        );
    }
}