package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.entity.Item;
import com.uade.tpo.marketplace.controllers.items.ItemRequest;
import com.uade.tpo.marketplace.repository.ItemRepository;
import com.uade.tpo.marketplace.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item crearItem(ItemRequest request) {
        Item item = new Item();
        item.setUsuario(request.getUsuario());
        item.setValor(request.getValor());
        item.setCantidad(request.getCantidad());
        item.setFechaHora(LocalDateTime.now());
        item.setProducto(request.getProducto());
        return itemRepository.save(item);
    }

    @Override
    public List<Item> listarItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item obtenerItemPorId(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + id));
    }

    @Override
    public void eliminarItem(String id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, item no encontrado con id: " + id);
        }
        itemRepository.deleteById(id);
    }
}
