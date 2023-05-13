package com.gigmanager.service;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import com.gigmanager.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> readAllItems(String username){
        return itemRepository.findAllByJob_Customer_ApiUser_username(username);
    }
}
