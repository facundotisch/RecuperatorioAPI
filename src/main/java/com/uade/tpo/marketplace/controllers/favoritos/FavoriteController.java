package com.uade.tpo.marketplace.controllers.favoritos;

import com.uade.tpo.marketplace.entity.Favorite;
import com.uade.tpo.marketplace.service.FavoriteService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        favoriteService.addFavorite(userId, productId);
        return ResponseEntity.ok("Producto agregado a favoritos");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorite>> getFavorites(@PathVariable String userId) {
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> removeFavorite(
            @PathVariable String userId,
            @PathVariable String productId
    ) {
        favoriteService.removeFavorite(userId, productId);
        return ResponseEntity.ok("Producto eliminado de favoritos");
    }
}
