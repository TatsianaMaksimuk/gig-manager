package com.gigmanager.controllers;

import com.gigmanager.models.Item;
import com.gigmanager.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemRepository itemRepository;

    //get all items
    @GetMapping("/items")
    public ResponseEntity<?> getAllItems(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Item> items = itemRepository.findAllByJob_Customer_ApiUser_username(username);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //get item by id

    //get all items by job id

    //get items by status and job id

    //create

    //update

    //delete



}