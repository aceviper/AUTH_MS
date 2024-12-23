package com.iwomi.authms.services.authenticate;

import com.iwomi.authms.domain.entities.User;
import com.iwomi.authms.dtos.AuthDto;
import com.iwomi.authms.core.response.AuthenticationResponse;
import com.iwomi.authms.dtos.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface IAuthenticateService {
    User signUp(UserDto dto);

    AuthenticationResponse signIn(AuthDto dto);

    Boolean validateToken(String token);

    Boolean checkTokenValidity(String token);

    String getUserIdFromToken(String token);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
