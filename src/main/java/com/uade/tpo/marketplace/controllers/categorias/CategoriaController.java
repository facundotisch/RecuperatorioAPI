package com.uade.tpo.marketplace.controllers.categorias;

import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaRequest request) throws Exception {
        Categoria categoria = categoriaService.crearCategoria(request);

        return new ResponseEntity<>(categoria, HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable String id, @RequestBody CategoriaRequest request) {
        Categoria categoria = categoriaService.actualizarCategoria(id, request);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaPorId(@PathVariable String id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable String id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
