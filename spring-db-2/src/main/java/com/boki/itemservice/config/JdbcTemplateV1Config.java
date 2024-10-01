package com.boki.itemservice.config;

import com.boki.itemservice.repository.ItemRepository;
import com.boki.itemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV1;
import com.boki.itemservice.repository.memory.MemoryItemRepository;
import com.boki.itemservice.service.ItemService;
import com.boki.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }

}
