package com.gigmanager.service;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import com.gigmanager.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> readAllItems(String username){
        return itemRepository.findAllByJob_Customer_ApiUser_username(username);
    }

    public Item findItemById(Long id, String username){
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null || !item.getJob().getCustomer().getApiUser().getUsername().equals(username)) {
            return null;
        }
        return item;
    }
}
