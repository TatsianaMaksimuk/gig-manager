package com.gigmanager.repositories;

import com.gigmanager.models.Item;
import com.gigmanager.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository <Item, Long> {
    List<Item> findAllByJob_ApiUser_usernameAndJob_id(String username, Long id);
}
