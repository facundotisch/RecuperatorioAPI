package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controllers.items.CarritoRequest;
import com.uade.tpo.marketplace.controllers.items.ItemRequest;
import com.uade.tpo.marketplace.entity.Compra;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.controllers.compras.CompraRequest;
import com.uade.tpo.marketplace.exceptions.CompraNotFoundException;

import java.util.List;

public interface CompraService {
    Compra crearCompra(CompraRequest compraRequest);

    List<Compra> findAll();

    List<Compra> findAllByUsuario(Usuario usuario);

    Compra findById(String id) throws CompraNotFoundException;

    List<Compra> findByFecha(java.time.LocalDateTime fecha);

    void deleteCompra(String id) throws CompraNotFoundException;

    Compra checkout(String email);

    Compra agregarAlCarrito(String usuarioEmail, CarritoRequest carritoRequest);
}
