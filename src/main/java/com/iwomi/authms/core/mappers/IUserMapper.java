package com.iwomi.authms.core.mappers;

import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.dtos.UserDto;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IUserMapper {
//    BranchDto mapToDto(BranchEntity entity);
//    UserEntity mapToEntity(BranchDto dto);
    User mapToModel(UserEntity entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget UserEntity entity);
}