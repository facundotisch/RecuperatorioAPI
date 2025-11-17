package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Favorite;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.repository.FavoriteRepository;
import com.uade.tpo.marketplace.repository.ProductoRepository;
import com.uade.tpo.marketplace.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public void addFavorite(String userId, String productId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        boolean exists = favoriteRepository.findByUsuarioAndProducto(usuario, producto).isPresent();

        if (!exists) {
            Favorite favorite = Favorite.builder()
                    .usuario(usuario)
                    .producto(producto)
                    .build();

            favoriteRepository.save(favorite);
        }
    }

    public List<Favorite> getFavorites(String userId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return favoriteRepository.findByUsuario(usuario);
    }

    public void removeFavorite(String userId, String productId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        favoriteRepository.deleteByUsuarioAndProducto(usuario, producto);
    }
}
