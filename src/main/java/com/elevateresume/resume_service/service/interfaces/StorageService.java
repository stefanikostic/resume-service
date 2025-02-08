package com.elevateresume.resume_service.service.interfaces;

import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    ResumeUploadDataDTO uploadFile(MultipartFile file) throws IOException;
    String getLatestVersionOfResume(String objectKey) throws IOException;
}
