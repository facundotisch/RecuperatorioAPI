package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Item;
import com.uade.tpo.marketplace.controllers.items.ItemRequest;

import java.util.List;

public interface ItemService {
    Item crearItem(ItemRequest request);
    List<Item> listarItems();
    Item obtenerItemPorId(String id);
    void eliminarItem(String id);
}
