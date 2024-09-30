package com.boki.itemservice.config;

import com.boki.itemservice.repository.ItemRepository;
import com.boki.itemservice.repository.memory.MemoryItemRepository;
import com.boki.itemservice.service.ItemService;
import com.boki.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
