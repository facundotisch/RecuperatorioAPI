package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.enums.Estados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    Optional<Producto> findByNombreEqualsIgnoreCase(String nombre);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByCategoriaId(String categoriaId);

    List<Producto> findAll();

    List<Producto> findByEstado(Estados estado);

    List<Producto> findByCategoria_NombreIgnoreCase(String nombreCategoria);

    List<Producto> findByNombreContainingIgnoreCaseOrCategoria_NombreContainingIgnoreCase(String nombre, String categoria);

}
