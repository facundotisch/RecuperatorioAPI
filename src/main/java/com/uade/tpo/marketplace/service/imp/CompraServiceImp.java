package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.controllers.items.CarritoRequest;
import com.uade.tpo.marketplace.controllers.items.ItemRequest;
import com.uade.tpo.marketplace.entity.Compra;
import com.uade.tpo.marketplace.entity.Item;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.controllers.compras.CompraRequest;
import com.uade.tpo.marketplace.enums.EstadoCompra;
import com.uade.tpo.marketplace.exceptions.CompraNotFoundException;
import com.uade.tpo.marketplace.exceptions.UsuarioNotFoundException;
import com.uade.tpo.marketplace.repository.CompraRepository;
import com.uade.tpo.marketplace.repository.ItemRepository;
import com.uade.tpo.marketplace.repository.ProductoRepository;
import com.uade.tpo.marketplace.repository.UsuarioRepository;
import com.uade.tpo.marketplace.service.CompraService;
import com.uade.tpo.marketplace.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImp implements CompraService {

    private final CompraRepository compraRepository;
    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final ProductoRepository productoRepository;

    @Autowired
    public CompraServiceImp(CompraRepository compraRepository,
                            ItemRepository itemRepository,
                            UsuarioRepository usuarioRepository,
                            UsuarioService usuarioService,
                            ProductoRepository productoRepository) {
        this.compraRepository = compraRepository;
        this.itemRepository = itemRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.productoRepository = productoRepository;
    }

    @Override
    public Compra crearCompra(CompraRequest compraRequest) {
        Usuario usuario = usuarioService.findById(compraRequest.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con ID: " + compraRequest.getUsuarioId()));

        Compra compra = new Compra();
        compra.setValor(compraRequest.getValor());
        compra.setUsuario(usuario);
        compra.setItems(compraRequest.getItems());
        compra.setFechaHora(compraRequest.getHora() != null ? compraRequest.getHora() : LocalDateTime.now());

        return compraRepository.save(compra);
    }

    @Override
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Override
    public List<Compra> findAllByUsuario(Usuario usuario) {
        return compraRepository.findByUsuario(usuario);
    }

    @Override
    public Compra findById(String id) throws CompraNotFoundException {
        return compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException());
    }

    @Override
    public List<Compra> findByFecha(LocalDateTime fecha) {
        return compraRepository.findByFechaHoraBetween(fecha.toLocalDate().atStartOfDay(), fecha.toLocalDate().atTime(23,59,59));
    }

    @Override
    public void deleteCompra(String id) throws CompraNotFoundException {
        if (!compraRepository.existsById(id)) {
            throw new CompraNotFoundException();
        }
        compraRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Compra agregarAlCarrito(String usuarioEmail, CarritoRequest carritoRequest) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar carrito existente o crear uno nuevo
        Compra carrito = compraRepository.findByUsuarioAndEstado(usuario, EstadoCompra.PENDIENTE)
                .orElseGet(() -> {
                    Compra nuevaCompra = new Compra();
                    nuevaCompra.setUsuario(usuario);
                    nuevaCompra.setEstado(EstadoCompra.PENDIENTE);
                    nuevaCompra.setFechaHora(LocalDateTime.now());
                    nuevaCompra.setValor(0.0f);
                    nuevaCompra.setItems(new ArrayList<>());
                    return compraRepository.save(nuevaCompra);
                });

        // Buscar el producto por ID usando productoId del request
        Producto producto = productoRepository.findById(carritoRequest.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + carritoRequest.getProductoId()));

        // Verificar si el item ya existe en el carrito
        Optional<Item> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(producto.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            // Actualizar cantidad del item existente
            Item item = itemExistente.get();
            item.setCantidad(item.getCantidad() + carritoRequest.getCantidad());
        } else {
            // Crear nuevo item y asignarle todas las propiedades necesarias
            Item item = new Item();
            item.setProducto(producto);
            item.setCantidad(carritoRequest.getCantidad());
            item.setCompra(carrito);
            item.setUsuario(usuario);
            item.setValor(producto.getValor());
            carrito.getItems().add(item);
        }

        // Calcular total
        float total = (float) carrito.getItems().stream()
                .mapToDouble(item -> item.getCantidad() * item.getProducto().getValor())
                .sum();
        carrito.setValor(total);

        return compraRepository.save(carrito);
    }

    @Override
    @Transactional
    public Compra checkout(String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Compra compra = compraRepository.findByUsuarioAndEstado(usuario, EstadoCompra.PENDIENTE)
                .orElseThrow(() -> new IllegalStateException("El carrito está vacío"));

        if (compra.getItems() == null || compra.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        for (Item item : compra.getItems()) {
            Producto producto = item.getProducto();
            int cantidadSolicitada = item.getCantidad();
            int stockDisponible = producto.getCantidad();

            if (stockDisponible >= cantidadSolicitada) {
                producto.setCantidad(stockDisponible - cantidadSolicitada);
                productoRepository.save(producto);
            } else {
                // No hay suficiente stock, lanzar una excepción
                throw new RuntimeException("No hay suficiente stock para el producto: " + producto.getNombre());
            }
        }

        compra.setEstado(EstadoCompra.CONFIRMADA);
        compra.setFechaHora(LocalDateTime.now());

        return compraRepository.save(compra);
    }
}
