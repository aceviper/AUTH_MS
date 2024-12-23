package com.iwomi.authms.frameworks.externals.clients;


import com.iwomi.authms.core.config.FeignSupportConfig;
import com.iwomi.authms.dtos.UpdateUploadDto;
import com.iwomi.authms.frameworks.externals.enums.FileTypeEnum;
import com.iwomi.authms.frameworks.externals.models.FileModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "file", url = "${externals.base-url.file-management}", configuration = FeignSupportConfig.class)
public interface FileManagementClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<FileModel>> fileUpload(@RequestParam("userId") String userId,
                                               @RequestPart("files") MultipartFile[] files);

    @PostMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<FileModel>> filesUpdate(@RequestBody List<UpdateUploadDto> dtos,
                                                @RequestParam String userid);

    @GetMapping("/show/{filename}")
    ResponseEntity<ByteArrayResource> findFile(@PathVariable("filename") String filename);

    @GetMapping("/{cli}")
    ResponseEntity<List<FileModel>> userFiles(@PathVariable("cli") String cli);

    @GetMapping("/count/{cli}")
    ResponseEntity<Map<FileTypeEnum, Long>> userFilesCount(@PathVariable("cli") String cli);
}
