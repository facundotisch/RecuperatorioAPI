package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.controllers.productos.ProductoAtributoRequest;
import com.uade.tpo.marketplace.controllers.productos.ProductoRequest;
import com.uade.tpo.marketplace.controllers.productos.ProductoUpdateRequest;
import com.uade.tpo.marketplace.entity.Atributo;
import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.entity.ValorAtributo;
import com.uade.tpo.marketplace.enums.Estados;
import com.uade.tpo.marketplace.exceptions.ProductoDuplicadoException;
import com.uade.tpo.marketplace.exceptions.ProductoExistenteException;
import com.uade.tpo.marketplace.repository.AtributoRepository;
import com.uade.tpo.marketplace.repository.ProductoRepository;
import com.uade.tpo.marketplace.repository.ValorAtributoProducto;
import com.uade.tpo.marketplace.service.CategoriaService;
import com.uade.tpo.marketplace.service.ProductoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImp implements ProductoService {

    private final ProductoRepository productoRepository;
    private final AtributoRepository atributoRepository;
    private final CategoriaService categoriaService;

    public ProductoServiceImp(ProductoRepository productoRepository, ValorAtributoProducto valorAtributoProductoRepository, AtributoRepository atributoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.atributoRepository = atributoRepository;
        this.categoriaService = categoriaService;
    }

    @Override
    public Producto crearProducto(ProductoRequest productoRequest) throws ProductoDuplicadoException {
        System.out.println(productoRequest);

        // Verificar duplicado
        Optional<Producto> existente = productoRepository.findByNombreEqualsIgnoreCase(productoRequest.getNombre());
        if (existente.isPresent()) {
            throw new ProductoDuplicadoException();
        }

        Producto producto = new Producto();
        producto.setNombre(productoRequest.getNombre());
        producto.setCantidad(productoRequest.getCantidad());
        producto.setCategoria(productoRequest.getCategoria());
        producto.setDescripcion(productoRequest.getDescripcion());
        producto.setFoto(productoRequest.getFoto());
        producto.setValor(productoRequest.getValor());
        producto.setDescuento(productoRequest.getDescuento());
        producto.setFechaHora(LocalDateTime.now());

        if (productoRequest.getDatos() != null && !productoRequest.getDatos().isEmpty()) {
            List<ValorAtributo> datosCompletos = new ArrayList<>();

            for (ProductoAtributoRequest datoRequest : productoRequest.getDatos()) {
                if (datoRequest.getAtributoNombre() == null || datoRequest.getValor() == null) {
                    throw new IllegalArgumentException("AtributoNombre y Valor no pueden ser null");
                }

                // Buscar el atributo por nombre
                Atributo atributo = atributoRepository.findByNombre(datoRequest.getAtributoNombre());
                if (atributo == null) {
                    // Crear el atributo si no existe
                    atributo = new Atributo();
                    atributo.setNombre(datoRequest.getAtributoNombre());
                    atributo = atributoRepository.save(atributo);
                }

                ValorAtributo datoCompleto = new ValorAtributo();
                datoCompleto.setValor(datoRequest.getValor());
                datoCompleto.setAtributo(atributo);
                datoCompleto.setProducto(producto);

                datosCompletos.add(datoCompleto);
            }

            producto.setDatos(datosCompletos);
        }

        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> getProductos() {
        // Usa el enum directamente
        return productoRepository.findByEstado(Estados.ACTIVO);
    }

    @Override
    public List<Producto> getTodosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent() && !"ACTIVO".equals(producto.get().getEstado())) {
            return Optional.empty();
        }
        return producto;
    }

    @Override
    public Optional<Producto> findByNombre(String nombre) {
        return productoRepository.findAll()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    @Override
    public List<Producto> findByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .filter(p -> Estados.ACTIVO.equals(p.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByCategoriaId(String categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Producto> findByCategoriaIdWithValidation(String categoriaId) {
        Categoria categoria = categoriaService.findById(categoriaId);
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    public Producto actualizarProducto(String id, ProductoUpdateRequest updateRequest) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    // Verificar si el nombre fue cambiado
                    if (updateRequest.getNombre() != null &&
                            !productoExistente.getNombre().equalsIgnoreCase(updateRequest.getNombre())) {

                        Optional<Producto> productoConMismoNombre = productoRepository
                                .findByNombreEqualsIgnoreCase(updateRequest.getNombre());

                        if (productoConMismoNombre.isPresent() && !productoConMismoNombre.get().getId().equals(id)) {
                            throw new RuntimeException(new ProductoDuplicadoException());
                        }
                        productoExistente.setNombre(updateRequest.getNombre());
                    }

                    // Actualizar solo los campos que vienen en el request (no nulos)
                    if (updateRequest.getDescripcion() != null) {
                        productoExistente.setDescripcion(updateRequest.getDescripcion());
                    }
                    if (updateRequest.getValor() != null && updateRequest.getValor() > 0) {
                        productoExistente.setValor(updateRequest.getValor());
                    }
                    if (updateRequest.getCantidad() != null && updateRequest.getCantidad() >= 0) {
                        productoExistente.setCantidad(updateRequest.getCantidad());
                    }
                    if (updateRequest.getDescuento() != null && updateRequest.getDescuento() >= 0) {
                        productoExistente.setDescuento(updateRequest.getDescuento());
                    }

                    return productoRepository.save(productoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }


    @Override
    public void desactivarProducto(String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(Estados.INACTIVO);
        productoRepository.save(producto);
    }

    @Override
    public void activarProducto(String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(Estados.ACTIVO);
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> buscarPorNombreOCategoria(String query) {
        return productoRepository.findByNombreContainingIgnoreCaseOrCategoria_NombreContainingIgnoreCase(query, query);
    }

    @Override
    public List<Producto> findByCategoriaNombre(String nombreCategoria) {
        return productoRepository.findByCategoria_NombreIgnoreCase(nombreCategoria);
    }


}
