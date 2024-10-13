package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.ProductEntity;
import com.example.marketmanagement.model.ProductDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapToDto(ProductEntity productEntity);

    ProductEntity mapToEntity(ProductDto productDto);
}
