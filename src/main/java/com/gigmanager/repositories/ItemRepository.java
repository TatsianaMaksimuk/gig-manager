package com.gigmanager.repositories;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import com.gigmanager.models.enums.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByJob_Customer_ApiUser_username(String username);

    List<Item> findAllByJob_Customer_ApiUser_usernameAndJob_id(Long id, String username );

    List<Item> findAllByJob_Customer_ApiUser_usernameAndJob_idAndStatus(Long id, ItemStatus status, String username);
}
