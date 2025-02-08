package com.elevateresume.resume_service.controller;

import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.service.interfaces.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final StorageService storageService;

    @GetMapping("/user")
    public ResponseEntity<Resume> getResumeByUserId() throws IOException {
        Optional<Resume> resumeOptional = resumeService.getResumeByUserId();
        if (resumeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Resume resume = resumeOptional.get();
        String s3FileName = resume.getS3FileName();
        String content = storageService.getLatestVersionOfResume(s3FileName);
        return ResponseEntity.ok(resume);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("resume") MultipartFile file) throws IOException {
        ResumeUploadDataDTO resumeUploadDataDTO = storageService.uploadFile(file);

        resumeService.createResume(resumeUploadDataDTO, file);

        return ResponseEntity.ok("Resume uploaded successfully: " + resumeUploadDataDTO);
    }
}
