package com.uade.tpo.marketplace.controllers.items;

import com.uade.tpo.marketplace.entity.Item;
import com.uade.tpo.marketplace.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> crearItem(@RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.crearItem(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<Item>> listarItems() {
        return ResponseEntity.ok(itemService.listarItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> obtenerItemPorId(@PathVariable String id) {
        return ResponseEntity.ok(itemService.obtenerItemPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable String id) {
        itemService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}
