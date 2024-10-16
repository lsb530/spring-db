package com.boki.itemservice.config;

import com.boki.itemservice.repository.ItemRepository;
import com.boki.itemservice.repository.jpa.JpaItemRepository;
import com.boki.itemservice.repository.mybatis.ItemMapper;
import com.boki.itemservice.repository.mybatis.MybatisItemRepository;
import com.boki.itemservice.service.ItemService;
import com.boki.itemservice.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }

}
