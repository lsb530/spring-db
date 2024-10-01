package com.boki.itemservice;

import com.boki.itemservice.config.JdbcTemplateV1Config;
import com.boki.itemservice.config.MemoryConfig;
import com.boki.itemservice.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

//@Import(MemoryConfig.class)
@Import(JdbcTemplateV1Config.class)
@SpringBootApplication(scanBasePackages = "com.boki.itemservice.web")
public class SpringDb2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringDb2Application.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}
}
