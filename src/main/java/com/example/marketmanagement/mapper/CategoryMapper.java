package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.CategoryEntity;
import com.example.marketmanagement.model.CategoryDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapToDto(CategoryEntity categoryEntity);

    CategoryEntity mapToEntity(CategoryDto categoryDto);
}
