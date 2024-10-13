package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.UserProfileEntity;
import com.example.marketmanagement.model.UserProfileReqDto;
import com.example.marketmanagement.model.UserProfileResDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileReqDto mapToDtoReq(UserProfileEntity userProfileEntity);

    UserProfileEntity mapToEntityReq(UserProfileReqDto userProfileDto);

    UserProfileResDto mapToDtoRes(UserProfileEntity userProfileEntity);
}

