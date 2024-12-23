package com.iwomi.authms.core.mappers;

import com.iwomi.authms.dtos.MerchantTellerDto;
import com.iwomi.authms.frameworks.data.entities.MerchantTeller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface IMerchantTellerMapper {

    MerchantTellerDto mapToDto(MerchantTeller merchantTeller);

    MerchantTeller mapToEntity(MerchantTellerDto merchantTellerDto);
}
