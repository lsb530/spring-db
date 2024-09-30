package com.boki.itemservice.service;

import com.boki.itemservice.domain.Item;
import com.boki.itemservice.repository.ItemSearchCond;
import com.boki.itemservice.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
