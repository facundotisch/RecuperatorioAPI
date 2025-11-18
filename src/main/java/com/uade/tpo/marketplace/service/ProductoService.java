package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controllers.productos.ProductoRequest;
import com.uade.tpo.marketplace.controllers.productos.ProductoUpdateRequest;
import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.exceptions.ProductoDuplicadoException;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto crearProducto(ProductoRequest productoRequest) throws ProductoDuplicadoException;

    List<Producto> getProductos();

    List<Producto> getTodosProductos();

    Optional<Producto> findById(String id);

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByCategoriaId(String categoriaId);

    List<Producto> findByCategoriaIdWithValidation(String categoriaId);

    Producto actualizarProducto(String id, ProductoUpdateRequest producto);

    void desactivarProducto(String id);
    void activarProducto(String id);

    List<Producto> buscarPorNombreOCategoria(String query);
    List<Producto> findByCategoriaNombre(String nombreCategoria);
}
