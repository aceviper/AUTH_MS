package com.iwomi.authms.dtos;

import com.iwomi.authms.core.enums.UserTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record UserDto(
        String email,

        String phoneNumber,

        @NotNull(message = "password is mandatory")
        @NotBlank(message = "password name is mandatory")
        @Size(min = 6, max = 12, message = "password must be between 6 to 12 characters")
        String password,

        @NotNull(message = "pin is mandatory")
        @NotBlank(message = "pin is mandatory")
        @Size(min = 4, max = 4, message = "pin must be of 4 characters")
        String pin,

        UserTypeEnum role,
        MultipartFile[] files,
        String client_id,

        String clientCode
) {
}
