package com.uade.tpo.marketplace.controllers.categorias;

import com.uade.tpo.marketplace.enums.Categorias;
import com.uade.tpo.marketplace.enums.Estados;
import lombok.Data;

@Data
public class CategoriaRequest {
    private String id;
    private String nombre;
    private String descripcion;
    private Categorias categoria;
    private Estados estado;
}
