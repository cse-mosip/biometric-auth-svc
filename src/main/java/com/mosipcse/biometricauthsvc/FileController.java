package com.mosipcse.biometricauthsvc;

import com.mosipcse.fileHandler.FileUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    FileController() {

    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // Get the file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Define the file path
            String baseDirectory = System.getProperty("user.home");
            String targetDirectory = "Desktop/fingerPrints";

            // creating target directory if it does not exist
            Path fullPath = Paths.get(baseDirectory, targetDirectory);

            try {
                if (!Files.exists(fullPath)) {
                    Files.createDirectories(fullPath);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file: Unable to " +
                        "create target directory");
            }

            // Define the file path within the target directory
            Path filePath = fullPath.resolve(fileName);

            // Save the file to the specified path
            Files.copy(file.getInputStream(), filePath);
            String responseMessage = "File saved successfully.";
            FileUploadResponse response = new FileUploadResponse(responseMessage, filePath.toString());

            return ResponseEntity.ok(response);

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file");
        }
    }
}