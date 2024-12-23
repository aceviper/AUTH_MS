package com.iwomi.authms.frameworks.externals.models;

import java.util.UUID;

public record ClientModel(
        UUID id,
        String branch,
        String email,
        String registrationNumber,
        String registrationDate
) {
}
