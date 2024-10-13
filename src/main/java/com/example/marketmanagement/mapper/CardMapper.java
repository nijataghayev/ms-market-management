package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.CardEntity;
import com.example.marketmanagement.model.CardDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardEntity mapToEntity(CardDto cardDto);

    CardDto mapToDto(CardEntity cardEntity);
}
