package com.ddk.asmsof306.restController;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/files")
public class FileController {

    @GetMapping("/fetch")
    public ResponseEntity<byte[]> fetchFile(@RequestParam String url) {
        try {
            Path filePath = Paths.get("src/main/resources/static/img/product/"+url);
            byte[] fileData = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(contentType));
            headers.setContentLength(fileData.length);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filePath.getFileName().toString()).build());
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
