
package com.lostfound.service;

import com.lostfound.entity.Item;
import com.lostfound.repository.ItemRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Save a new item
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    // Get all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Get one item by ID
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElse(null);
    }

    // Update item status (e.g., OPEN -> RETURNED)
    public Item updateItemStatus(Long id, String status) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setStatus(status);
        return itemRepository.save(item);
    }
    // By alif
}
