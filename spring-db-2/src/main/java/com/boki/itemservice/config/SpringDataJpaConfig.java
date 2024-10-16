package com.boki.itemservice.config;

import com.boki.itemservice.repository.ItemRepository;
import com.boki.itemservice.repository.jpa.JpaItemRepositoryV2;
import com.boki.itemservice.repository.jpa.SpringDataJpaItemRepository;
import com.boki.itemservice.service.ItemService;
import com.boki.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository springDataJpaItemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(springDataJpaItemRepository);
    }

}
