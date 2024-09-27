package com.ddk.asmsof306.service;

import com.ddk.asmsof306.restController.UploadRestController;
import com.sun.mail.imap.protocol.UID;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class UploadService {
    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public String save(MultipartFile file) throws IOException {
        // Get the file name
        String fileName = "file_" + UUID.randomUUID() + new Date().toInstant() + ".jpg";
        fileName = fileName.replace(":", "");

        // Define the file path to save in resource/static/img directory
        Path filePath = Paths.get("src/main/resources/static/img/product/"+fileName );

        // Copy the file to the specified location
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the file URL
        return  fileName;
    }
}
