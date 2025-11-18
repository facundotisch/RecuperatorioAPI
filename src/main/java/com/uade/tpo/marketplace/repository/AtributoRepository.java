package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Atributo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtributoRepository extends JpaRepository<Atributo, String> {
    Atributo findByNombre(String nombre);
}