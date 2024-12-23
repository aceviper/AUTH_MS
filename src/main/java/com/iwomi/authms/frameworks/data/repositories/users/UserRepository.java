package com.iwomi.authms.frameworks.data.repositories.users;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.core.errors.exceptions.UserAlreadyExistsException;
import com.iwomi.authms.core.errors.exceptions.UserNotFoundException;
import com.iwomi.authms.core.mappers.IUserMapper;
import com.iwomi.authms.dtos.UserDto;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final IUserRepository repository;
    private final IUserMapper mapper;

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public UserEntity createUser(UserEntity entity) {
//        BranchEntity entity = mapper.mapToEntity(dto);
        return repository.save(entity);
    }

    public UserEntity getOne(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserEntity getOneByClientCode(String clientCode) {
        return repository.findByClientCode(clientCode)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserEntity getOneByPhone(String phone) {
        return repository.findByPhoneNumber(phone)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public List<UserEntity>  getByRoleAndDeleted(UserTypeEnum role, Boolean state) {
          return  repository.findByRoleAndDeleted(role, state);
    }

    public List<UserEntity>  getByRole(UserTypeEnum role) {
        return  repository.findByRole(role);
    }

    public void isUserFound(String phone) {
        boolean exists = repository.existsUsersEntityByPhoneNumber(phone);
        if (exists) throw new UserAlreadyExistsException("Sorry user "+phone+" already exists");
    }

//    public UserEntity updateUser(UUID uuid, UserDto dto) {
//        UserEntity entity = getOne(uuid);
//        mapper.updateUserFromDto(dto, entity);
//        return repository.save(entity);
//    }

    public UserEntity changeIsFirstTime(UUID uuid) {
        UserEntity entity = getOne(uuid);
        entity.setFirstSignIn(false);
        return repository.save(entity);
    }

    public void deleteUser(UUID uuid) {
        repository.deleteById(uuid);
    }

    public Boolean isUserSuspended(UUID uuid) {
        return repository.findById(uuid)
                .map(entity -> entity.getStatus() == StatusEnum.SUSPENDED)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
