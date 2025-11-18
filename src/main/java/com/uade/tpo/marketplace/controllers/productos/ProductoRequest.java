package com.uade.tpo.marketplace.controllers.productos;

import com.uade.tpo.marketplace.controllers.productos.ProductoAtributoRequest;
import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.enums.Estados;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ProductoRequest {
    private String id;
    private String nombre;
    private Date fecha;
    private LocalDateTime hora;
    private float valor;
    private String descripcion;
    private String foto;
    private int cantidad;
    private int descuento;
    private Categoria categoria;
    private Estados estado;
    @Valid
    private List<ProductoAtributoRequest> datos;

    public boolean isValid() {
        if (datos != null) {
            for (ProductoAtributoRequest dato : datos) {
                if (dato.getAtributoNombre() == null || dato.getValor() == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

