package com.uade.tpo.marketplace.controllers.compras;

import com.uade.tpo.marketplace.controllers.items.ItemResponse;
import com.uade.tpo.marketplace.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponse {
    private String id;
    private String usuarioEmail;
    private double total;
    private LocalDateTime fecha;
    private List<ItemResponse> items;

    public static CompraResponse fromEntity(Compra compra) {
        return new CompraResponse(
                compra.getId(),
                compra.getUsuario().getEmail(),
                compra.getValor(),
                compra.getFechaHora(),
                compra.getItems().stream()
                        .map(ItemResponse::fromEntity)
                        .toList()
        );
    }
}
