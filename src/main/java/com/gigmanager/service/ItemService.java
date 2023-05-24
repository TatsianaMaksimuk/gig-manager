package com.gigmanager.service;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import com.gigmanager.models.enums.ItemStatus;
import com.gigmanager.models.request.ItemUpsertRequest;
import com.gigmanager.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final JobService jobService;

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

    public List<Item>findAllItemsByJobId(Long jobId, String username){
        return itemRepository.findAllByJob_Customer_ApiUser_usernameAndJob_id(jobId, username);
    }

    public List<Item> findItemsByJobIdAndStatus(Long jobId, ItemStatus status, String username){
        return itemRepository.findAllByJob_Customer_ApiUser_usernameAndJob_idAndStatus(jobId, status, username);
    }

    public Item createNewItem(ItemUpsertRequest itemUpsertRequest, Long id, String username){
        Job job = jobService.findJobById(id, username);
        Item newItem = new Item();
        newItem.setJob(job);
        newItem.setName(itemUpsertRequest.getName());
        newItem.setDescription(itemUpsertRequest.getDescription());
        newItem.setStatus(ItemStatus.TO_DO);
        newItem.setStage(itemUpsertRequest.getStage());
        return newItem;
    }

    public Item updateItem(ItemUpsertRequest itemUpsertRequest, Long jobId, Long itemId, String username){
        Item item = findItemById(itemId, username);
        if (!item.getJob().getId().equals(jobId)){
            return null;
        }
        item.setName(itemUpsertRequest.getName());
        item.setDescription(itemUpsertRequest.getDescription());
        item.setStatus(itemUpsertRequest.getStatus());
        item.setStage(itemUpsertRequest.getStage());
        item.setFinished(itemUpsertRequest.isFinished());
        itemRepository.save(item);
        return item;
    }

    public void deleteItem(Long id, String username){
        Item item = findItemById(id, username);
        if (item != null){
            itemRepository.deleteById(id);
        }
    }
}
