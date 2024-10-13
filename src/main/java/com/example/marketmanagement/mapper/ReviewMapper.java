package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.ReviewEntity;
import com.example.marketmanagement.model.ReviewReqDto;
import com.example.marketmanagement.model.ReviewResDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewResDto mapToDto(ReviewEntity reviewEntity);

    ReviewEntity mapToEntityRes(ReviewResDto reviewDto);

    ReviewEntity mapToEntityReq(ReviewReqDto reviewDto);
}
