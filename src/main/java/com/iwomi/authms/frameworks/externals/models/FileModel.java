package com.iwomi.authms.frameworks.externals.models;

import com.iwomi.authms.frameworks.externals.enums.FileTypeEnum;

public record FileModel (
         String name,
         String ext,
         String url,
         FileTypeEnum type,
         String userId
) {

}
