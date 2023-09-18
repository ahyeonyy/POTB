package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {
	@PostMapping("/upload-endpoint")
    public ResponseEntity<?> handleFileUpload(@RequestParam("files[]") MultipartFile[] files) {
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();

            // 여기서 파일을 저장하거나 다른 작업을 수행할 수 있습니다.
            try {
                file.transferTo(new File("path_to_save_directory/" + name));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Failed to upload file: " + name);
            }
        }

        return ResponseEntity.ok("Files uploaded successfully");
    }
}
