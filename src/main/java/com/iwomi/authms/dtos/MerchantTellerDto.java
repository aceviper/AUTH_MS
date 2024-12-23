package com.iwomi.authms.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.UUID;

public record MerchantTellerDto(
        @NotNull(message = "user id is mandatory")
        @NotBlank(message = "user id name is mandatory")
        String userId,
        @NotNull(message = "merchant id is mandatory")
        @NotBlank(message = "merchant id name is mandatory")
        String merchantId,
        @NotNull(message = "organisation name is mandatory")
        @NotBlank(message = "organisation name name is mandatory")
        String organisationName
) {
}
