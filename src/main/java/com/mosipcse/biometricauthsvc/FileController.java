package com.mosipcse.biometricauthsvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class FileController {

    FileController() {

    }

    @PostMapping("/upload")

    public ResponseEntity<Object> uploadDataWithIndex(@RequestBody UploadRequest uploadRequest) {

        try {
            String projectDirectory = System.getProperty("user.dir"); // Get the project directory
            String targetDirectory = "fingerprints"; // Name of the target directory

            // Creating target directory if it does not exist
            Path fullPath = Paths.get(projectDirectory, targetDirectory);

            try {
                if (!Files.exists(fullPath)) {
                    Files.createDirectories(fullPath);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data: Unable to " +
                        "create target directory");
            }

            String index = uploadRequest.getIndex();

            if (index == null || index.isEmpty()) {
                String random;
                Path randomPath;
                do {
                    random = String.valueOf(new Random().nextInt(100000));
                    randomPath = fullPath.resolve(random);
                } while (Files.exists(randomPath));

                for (Data entry : uploadRequest.getData()) {

                    String imageFileName = entry.getBioSubType().replace(" ", "_") + ".jpg";

                    Path subFolderPath = fullPath.resolve(random);
                    Files.createDirectories(subFolderPath);

                    Path imagePath = subFolderPath.resolve(imageFileName);
                    Files.write(imagePath, entry.getBuffer().getData());
                }
            } else {
                for (Data entry : uploadRequest.getData()) {
                    // Create a subfolder based on the provided index
                    Path subFolderPath = fullPath.resolve(index);
                    Files.createDirectories(subFolderPath);

                    // Create a unique filename for the image
                    String imageFileName = entry.getBioSubType().replace(" ", "_") + ".jpg";
                    Path imagePath = subFolderPath.resolve(imageFileName);

                    // Save the image buffer as a JPEG file
                    Files.write(imagePath, entry.getBuffer().getData());

                    // You can now do something with the imagePath if needed
                }
            }

            String responseMessage = "Files saved successfully.";
            Map<String, String> response = new HashMap<>();
            response.put("message", responseMessage);

            return ResponseEntity.ok(response);

        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save files");
        }
    }
}

class UploadRequest {
    private String index;
    private List<Data> data;

    public String getIndex() {
        return index;
    }

    public List<Data> getData() {
        return data;
    }

    // Constructors, getters, and setters for index and data
}

class Data {
    private Buffer buffer;
    private String bioSubType;

    public String getBioSubType() {

        return bioSubType;
    }

    public Buffer getBuffer() {

        return buffer;
    }

    // Constructors, getters, and setters for buffer and bioSubType
}

class Buffer {
    private String type;
    private byte[] data;

    public byte[] getData() {

        return data;
    }

    // Constructors, getters, and setters for type and data
}