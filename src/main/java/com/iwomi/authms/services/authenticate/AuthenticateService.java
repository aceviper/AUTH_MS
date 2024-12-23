package com.iwomi.authms.services.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.authms.core.errors.exceptions.UserAlreadyExistsException;
import com.iwomi.authms.core.errors.exceptions.UserNotFoundException;
import com.iwomi.authms.core.errors.exceptions.WrongVerificationCodeException;
import com.iwomi.authms.core.mappers.IUserMapper;
import com.iwomi.authms.core.response.AuthenticationResponse;
import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.dtos.AuthDto;
import com.iwomi.authms.dtos.UserDto;
import com.iwomi.authms.frameworks.data.entities.UserEntity;
import com.iwomi.authms.frameworks.data.repositories.users.IUserRepository;
import com.iwomi.authms.frameworks.data.repositories.users.UserRepository;
import com.iwomi.authms.frameworks.jwt.IJwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticateService implements IAuthenticateService {
    private final IUserRepository IUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IUserMapper userMapper;

    @Override
    public User signUp(UserDto dto) {
//        boolean ex = IUserRepository.existsUsersEntityByPhoneNumber(dto.getPhone());
//        if (ex) throw new UserAlreadyExistsException("Sorry user "+dto.getPhone()+" already exists");

        userRepository.isUserFound(dto.phoneNumber());

        UserEntity userEntity = UserEntity.builder()
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .password(passwordEncoder.encode(dto.password()))
                .status(StatusEnum.ACTIVE) // change back to DEACTIVATED
                .role(dto.role())
                .client_id(dto.client_id())
                .clientCode(dto.clientCode())
                .build();
        return userMapper.mapToModel(IUserRepository.save(userEntity));
    }

    @Override
    public AuthenticationResponse signIn(AuthDto dto) {
//        System.out.println("trying login");
        UserEntity userEntity = userRepository.getOneByPhone(dto.getPhone());
        System.out.println("user entity: "+ userEntity);
        System.out.println("user id: "+ userEntity.getUuid());
        if (userEntity.getStatus() == StatusEnum.DEACTIVATED)
            throw new UnAuthorizedException("Your account has not yet been activated. Please contact admin");
        if (userEntity.getStatus() == StatusEnum.SUSPENDED)
            throw new UnAuthorizedException("Your account has been suspended");

        boolean isCodeValid = passwordEncoder.matches(dto.getPassword(), userEntity.getPassword());
//        System.out.println("user entity: "+ userEntity);

        if (!isCodeValid) throw new WrongVerificationCodeException("Incorrect password provided");

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getPhone(),
                dto.getPassword()
            )
        );
        String jwtToken = jwtService.generateToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        // update first time
        userRepository.changeIsFirstTime(userEntity.getUuid());

        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Override
    public Boolean validateToken(String token) {
        System.out.println("gotten token "+token);
        String phone = jwtService.extractUsername(token);
        UserEntity userEntity = userRepository.getOneByPhone(phone);
        var result = jwtService.isTokenValid(token, userEntity);
        System.out.println("valid return: "+result);
        return result;
    }

    @Override
    public Boolean checkTokenValidity(String token) {
        return jwtService.isTokenValid(token);
    }

    @Override
    public String getUserIdFromToken(String token) {
        String phone = jwtService.extractUsername(token);
        UserEntity userEntity = userRepository.getOneByPhone(phone);
        return userEntity.getUuid().toString();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String phone;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        phone = jwtService.extractUsername(refreshToken);
        if (phone != null) {
            UserEntity userEntity = userRepository.getOneByPhone(phone);
//            if (userModel == null) throw new UserNotFoundException("User not found.");

            if (jwtService.isTokenValid(refreshToken, userEntity)) {
                String accessToken = jwtService.generateToken(userEntity);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


//    private void sendVerificationCode(String code) {
//        // send code
//        userEntity.setCodeSendTime(LocalTime.now());
//        userRepository.save(userEntity);
//    }

//    private void saveUserToken(UserModel user, String jwtToken) {
//        var token = TokenEntity.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenEnum(TokenEnum.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }

//    private void revokeAllUserTokens(UserModel user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUuid());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }
}
