package com.uade.tpo.marketplace.controllers.items;

import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.entity.Usuario;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ItemRequest {
    private String id;
    private Producto producto;
    private Usuario usuario;
    private int cantidad;
    private float valor;
    private Date fecha;
    private LocalDateTime hora;
}
