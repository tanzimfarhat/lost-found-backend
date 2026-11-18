
package com.lostfound.controller;

import com.lostfound.entity.Item;
import com.lostfound.service.ItemService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Create a new lost or found item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    // Get all reported items
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Get one item using its ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    // Update item status (admin only) - e.g., OPEN -> RETURNED
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Item updateItemStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return itemService.updateItemStatus(id, body.get("status"));
    }
    // By alif
}
