package com.elevateresume.resume_service.controller;

import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.service.interfaces.StorageService;
import com.elevateresume.resume_service.util.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/external/resume")
@RequiredArgsConstructor
public class ResumeExternalController {

    private final ResumeService resumeService;
    private final StorageService storageService;
    private final RequestContext requestContext;

    @GetMapping("/")
    public ResponseEntity<ResumeDTO> getResumeByUserId() {
        String userId = requestContext.getUserId();
        ResumeDTO resumeDTO = resumeService.getResumeByUserId(userId);
        if (resumeDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resumeDTO);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("resume") MultipartFile file) throws IOException {
        ResumeUploadDataDTO resumeUploadDataDTO = storageService.uploadFile(file);

        // call resume processor to process and extract resume
        // save in db and cache
        resumeService.createResume(resumeUploadDataDTO, file);

        return ResponseEntity.ok("Resume uploaded successfully: " + resumeUploadDataDTO);
    }
}
