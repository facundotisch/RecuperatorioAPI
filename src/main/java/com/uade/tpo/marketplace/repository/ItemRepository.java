package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Item;
import com.uade.tpo.marketplace.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByUsuarioAndCompraIsNull(Usuario usuario);
}
