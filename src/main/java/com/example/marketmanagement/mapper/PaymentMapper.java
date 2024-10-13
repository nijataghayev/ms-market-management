package com.example.marketmanagement.mapper;

import com.example.marketmanagement.dao.entity.PaymentEntity;
import com.example.marketmanagement.model.PaymentDto;
import org.mapstruct.Mapper;

/**
 * @author: nijataghayev
 */

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto mapToDto(PaymentEntity paymentEntity);

    PaymentEntity mapToEntity(PaymentDto paymentDto);
}
