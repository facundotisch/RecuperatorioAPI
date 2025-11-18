package com.uade.tpo.marketplace.controllers.favoritos;

import com.uade.tpo.marketplace.controllers.favoritos.FavoriteAddRequest;
import com.uade.tpo.marketplace.entity.Favorite;
import com.uade.tpo.marketplace.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites/10/10")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<String> addFavorite(@Valid @RequestBody FavoriteAddRequest request) {
        favoriteService.addFavorite(request.userId(), request.productId());
        return ResponseEntity.ok("Producto agregado a favoritos");
    }
    
    //usarID=10 productID=  10[nombre,precio,descuento,foto,categoria,estado]

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getFavorites(@PathVariable String userId) {
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @DeleteMapping
    public ResponseEntity<String> removeFavorite(@Valid @RequestBody FavoriteAddRequest request) {
        favoriteService.removeFavorite(request.userId(), request.productId());
        return ResponseEntity.ok("Producto eliminado de favoritos");
    }
}