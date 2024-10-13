package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.BasketEntity;
import com.example.marketmanagement.model.BasketDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface BasketMapper {

    BasketDto mapToDto(BasketEntity basketEntity);

    BasketEntity mapToEntity(BasketDto basketDto);
}
