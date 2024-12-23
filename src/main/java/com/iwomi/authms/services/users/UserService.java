package com.iwomi.authms.services.users;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import com.iwomi.authms.core.mappers.IUserMapper;
import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import com.iwomi.authms.frameworks.data.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updatePin(UUID uuid, String pin) {
        UserEntity entity = userRepository.getOne(uuid);
        entity.setPin(pin); //find a way to validate pin non null
        return userMapper.mapToModel(userRepository.createUser(entity));
    }

    @Override
    public User updateStatus(UUID uuid, StatusEnum status) {
        UserEntity entity = userRepository.getOne(uuid);
        entity.setStatus(status); //find a way to validate status non null
        return userMapper.mapToModel(userRepository.createUser(entity));
    }

    @Override
    public User updatePassword(UUID uuid, String password) {
        UserEntity entity = userRepository.getOne(uuid);
        entity.setPassword(passwordEncoder.encode(password)); //find a way to validate password non null
        return userMapper.mapToModel(userRepository.createUser(entity));
    }

    @Override
    public boolean isValidPin(String clientCode, String pin) {
        UserEntity entity = userRepository.getOneByClientCode(clientCode);
        return entity.getPin().equals(pin);
    }

    @Override
    public List<String> viewByRoleAndDeleted(UserTypeEnum role) {
        return userRepository.getByRoleAndDeleted(role, true)
                .stream()
                .map(UserEntity::getClientCode)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> viewByRole(UserTypeEnum role) {
      return  userRepository.getByRole(role)
              .stream()
              .map(UserEntity::getClientCode)
              .toList();
    }
}
