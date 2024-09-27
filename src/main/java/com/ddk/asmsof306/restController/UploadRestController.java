package com.ddk.asmsof306.restController;

import com.ddk.asmsof306.service.UploadService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadRestController {
    private final UploadService uploadService;
    public record FileUploadResponse(String uploadUrl) {
    }
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if the file is not empty

            if (file.isEmpty()) {
                return new ResponseEntity<>(new FileUploadResponse("File is empty"), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(new FileUploadResponse(uploadService.save(file)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileUploadResponse("Failed to upload file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
