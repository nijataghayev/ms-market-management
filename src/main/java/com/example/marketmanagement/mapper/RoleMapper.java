package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.RoleEntity;
import com.example.marketmanagement.model.RoleDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleEntity mapToEntity(RoleDto roleDto);

    RoleDto mapToDto(RoleEntity roleEntity);
}
