package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Favorite;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUsuarioAndProducto(Usuario usuario, Producto producto);

    List<Favorite> findByUsuario(Usuario usuario);

    long deleteByUsuarioAndProducto(Usuario usuario, Producto producto);
}