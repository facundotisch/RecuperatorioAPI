package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.entity.Favorite;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.exceptions.FavoriteExceptions.*;
import com.uade.tpo.marketplace.repository.FavoriteRepository;
import com.uade.tpo.marketplace.repository.ProductoRepository;
import com.uade.tpo.marketplace.repository.UsuarioRepository;
import com.uade.tpo.marketplace.service.FavoriteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImp implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void addFavorite(String userId, String productId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new FavoriteResourceNotFoundException("Usuario " + userId + " no existe"));

        Producto producto = productoRepository.findById(productId)
            .orElseThrow(() -> new FavoriteResourceNotFoundException("Producto " + productId + " no existe"));

        boolean exists = favoriteRepository.findByUsuarioAndProducto(usuario, producto).isPresent();
        if (exists) {
            throw new FavoriteAlreadyExistsException(
                "Producto " + productId + " ya está en favoritos del usuario " + userId);
        }

        favoriteRepository.save(Favorite.builder()
            .usuario(usuario)
            .producto(producto)
            .build());
    }

    @Override
    public List<Favorite> getFavorites(String userId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new FavoriteResourceNotFoundException("Usuario " + userId + " no existe"));
        return favoriteRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public void removeFavorite(String userId, String productId) {
        Usuario usuario = usuarioRepository.findById(userId)
            .orElseThrow(() -> new FavoriteResourceNotFoundException("Usuario " + userId + " no existe"));

        Producto producto = productoRepository.findById(productId)
            .orElseThrow(() -> new FavoriteResourceNotFoundException("Producto " + productId + " no existe"));

        long deleted = favoriteRepository.deleteByUsuarioAndProducto(usuario, producto);
        if (deleted == 0) {
            throw new FavoriteBadRequestException(
                "El producto " + productId + " no estaba en favoritos del usuario " + userId);
        }
    }
}