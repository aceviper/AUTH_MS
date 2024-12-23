package com.iwomi.authms.frameworks.data.repositories.users;


import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhoneNumber(String phone);
    Boolean existsUsersEntityByPhoneNumber(String phone);

   Optional<UserEntity > findByClientCode(String clientCode);

   List<UserEntity> findByRoleAndDeleted(UserTypeEnum role, Boolean state);

    List<UserEntity> findByRole(UserTypeEnum role);

}
