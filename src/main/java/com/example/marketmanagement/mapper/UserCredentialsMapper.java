package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.UserCredentialEntity;
import com.example.marketmanagement.model.UserCredentialsDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

    UserCredentialsDto mapToDto(UserCredentialEntity userEntity);

    UserCredentialEntity mapToEntity(UserCredentialsDto userCredentialsDto);
}
