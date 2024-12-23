package com.iwomi.authms.services.users;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.frameworks.data.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User updatePin(UUID uuid, String pin);
    User updateStatus(UUID uuid, StatusEnum status);
    User updatePassword(UUID uuid, String password);

    boolean isValidPin(String clientCode, String pin);

    List<String>  viewByRoleAndDeleted(UserTypeEnum role);

   List<String>  viewByRole(UserTypeEnum role);
}
