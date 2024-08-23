package com.sky.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class UploadUtils {
    @Autowired
    private UploadProperties uploadProperties;

    public String upload(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        int lastIndex = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(lastIndex);
        String newFileName = UUID.randomUUID().toString() + extName;
        File dir = new File("upload_images/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        image.transferTo(new File(dir.getCanonicalPath() + "/" + newFileName));
        String url = "http://" + uploadProperties.getAddress() + ":" +
                String.valueOf(uploadProperties.getPort()) + "/images/" + newFileName;
        return url;
    }
}
