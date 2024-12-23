package com.iwomi.authms.domain.entities;

import com.iwomi.authms.core.enums.StatusEnum;
import com.iwomi.authms.core.enums.UserTypeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private String email;
    private String phoneNumber;
    private StatusEnum status;
    private UserTypeEnum role;
    private MultipartFile[] file;
    private String client_id;   // client foreign key
    private boolean firstSignIn;
}
