package com.boki.itemservice.repository.mybatis;

import com.boki.itemservice.domain.Item;
import com.boki.itemservice.repository.ItemSearchCond;
import com.boki.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(
        @Param("id") Long id,
        @Param("updateParam") ItemUpdateDto updateParam
    );

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

}
