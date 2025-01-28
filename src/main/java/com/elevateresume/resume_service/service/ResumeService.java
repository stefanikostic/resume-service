package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.Resume;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResumeService {
    Mono<Resume> getResumeByUserId(String userId);
    Flux<Resume> getResumesByTemplate(String userId);
    void mapResumeFileToResume(MultipartFile resumeFile);
}
