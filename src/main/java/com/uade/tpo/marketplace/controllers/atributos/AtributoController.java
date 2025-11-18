package com.uade.tpo.marketplace.controllers.atributos;

import com.uade.tpo.marketplace.entity.Atributo;
import com.uade.tpo.marketplace.service.AtributoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atributos")
public class AtributoController {

    private final AtributoService atributoService;

    @Autowired
    public AtributoController(AtributoService atributoService) {
        this.atributoService = atributoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Atributo> crearAtributo(@RequestBody AtributoRequest request) {
        Atributo atributo = atributoService.crearAtributo(request);
        return new ResponseEntity<>(atributo, HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Atributo> actualizarAtributo(@PathVariable String id, @RequestBody AtributoRequest request) {
        Atributo atributo = atributoService.actualizarAtributo(id, request);
        return ResponseEntity.ok(atributo);
    }

    @GetMapping("/")
    public ResponseEntity<List<Atributo>> listarAtributos() {
        List<Atributo> atributos = atributoService.findAll();
        return ResponseEntity.ok(atributos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atributo> getAtributoPorId(@PathVariable String id) {
        Atributo atributo = atributoService.findById(id);
        return ResponseEntity.ok(atributo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAtributo(@PathVariable String id) {
        atributoService.eliminarAtributo(id);
        return ResponseEntity.noContent().build();
    }
}
