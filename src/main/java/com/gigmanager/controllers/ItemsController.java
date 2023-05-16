package com.gigmanager.controllers;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import com.gigmanager.models.enums.ItemStatus;
import com.gigmanager.models.request.ItemUpsertRequest;
import com.gigmanager.models.request.JobUpsertRequest;
import com.gigmanager.repositories.ItemRepository;
import com.gigmanager.repositories.JobRepository;
import com.gigmanager.service.CustomerService;
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
    private final JobRepository jobRepository;

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

    //Start here:

    //get all items by job id
    @GetMapping("jobs/{id}/items")
    public ResponseEntity<?> getAllItemsByJobId(@PathVariable long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemRepository.findAllByJob_Customer_ApiUser_usernameAndJob_id(username, id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    //get items by status and job id

    @GetMapping("jobs/{id}/items/filter")
    public ResponseEntity<?> getItemsByJobAndStatusFilter(@PathVariable long id, @RequestParam ItemStatus status, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemRepository.findAllByJob_Customer_ApiUser_usernameAndJob_idAndStatus(username, id, status);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    //create
    @PostMapping("jobs/{id}")
    public ResponseEntity<?> createItem(@RequestBody ItemUpsertRequest itemUpsertRequest, Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Job itemJob = jobRepository.getById(id);
        if (!itemJob.getCustomer().getApiUser().getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Item newItem = new Item();
        newItem.setJob(itemJob);
        newItem.setName(itemUpsertRequest.getName());
        newItem.setDescription(itemUpsertRequest.getDescription());
        newItem.setStatus(ItemStatus.TO_DO);
        newItem.setStage(itemUpsertRequest.getStage());
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    //update
    @PostMapping("jobs/{jobId}/items/{itemId}")
    public ResponseEntity<?> updateItem(@RequestBody ItemUpsertRequest itemUpsertRequest, Long jobId, long itemId, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Item item = itemRepository.getById(itemId);
        if (!item.getJob().getId().equals(jobId)
                || !item.getJob().getCustomer().getApiUser().getUsername().equals(username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        item.setName(itemUpsertRequest.getName());
        item.setDescription(itemUpsertRequest.getDescription());
        item.setStatus(itemUpsertRequest.getStatus());
        item.setStage(itemUpsertRequest.getStage());
        item.setFinished(itemUpsertRequest.isFinished());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //delete
    @DeleteMapping("item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Item requestedItem = itemRepository.findById(id).orElse(null);
        if (requestedItem != null && requestedItem.getJob().getCustomer().getApiUser().getUsername().equals(username)) {
            itemRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
