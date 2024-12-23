package com.iwomi.authms.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUploadDto {
    private MultipartFile file;
    private String previousFileName;
}
