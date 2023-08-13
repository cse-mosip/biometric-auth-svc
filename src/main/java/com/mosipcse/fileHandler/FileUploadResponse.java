package com.mosipcse.fileHandler;

public class FileUploadResponse {
    private String status;
    private String filePath;

    public FileUploadResponse(String status, String filePath) {
        this.status = status;
        this.filePath = filePath;
    }

    // Getters and setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
