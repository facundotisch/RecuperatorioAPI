package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Compra;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.enums.EstadoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, String> {
    List<Compra> findByUsuario(Usuario usuario);

    List<Compra> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    Optional<Compra> findByUsuarioAndEstado(Usuario usuario, EstadoCompra estadoCompra);
}
