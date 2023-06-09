package com.gigmanager.controllers;

import com.gigmanager.models.Item;
import com.gigmanager.models.enums.ItemStatus;
import com.gigmanager.models.request.ItemUpsertRequest;
import com.gigmanager.repositories.ItemRepository;
import com.gigmanager.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemService itemService;
    private final ItemRepository itemRepository;

    //get all items
    @GetMapping("/items")
    public ResponseEntity<?> getAllItems(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemService.readAllItems(username);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //get item by id
    @GetMapping("items/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Item requestedItem = itemService.findItemById(id, username);
        return new ResponseEntity<>(requestedItem, HttpStatus.OK);
    }


    //get all items by job id
    @GetMapping("jobs/{id}/items")
    public ResponseEntity<?> getAllItemsByJobId(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemService.findAllItemsByJobId(id, username);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //get items by status and job id

    @GetMapping("jobs/{id}/items/filter")
    public ResponseEntity<?> getItemsByJobAndStatusFilter(@PathVariable Long id, @RequestParam ItemStatus status, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemService.findItemsByJobIdAndStatus(id, status, username);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    //create
    @PostMapping("jobs/{id}/")
    public ResponseEntity<?> createItem(@RequestBody ItemUpsertRequest itemUpsertRequest, @PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Item newItem = itemService.createNewItem(itemUpsertRequest, id, username);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }


    //update
    @PostMapping("jobs/{jobId}/items/{itemId}")
    public ResponseEntity<?> updateItem(@RequestBody ItemUpsertRequest itemUpsertRequest, @PathVariable Long jobId, @PathVariable Long itemId, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Item updatedItem = itemService.updateItem(itemUpsertRequest, jobId, itemId, username);
        if (updatedItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        itemService.deleteItem(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
