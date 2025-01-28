package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.document.ResumeDocument;
import com.elevateresume.resume_service.dto.Resume;
import com.elevateresume.resume_service.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public Mono<Resume> getResumeByUserId(String userId) {
        return resumeRepository.findByUserId(userId);
    }

    @Override
    public Flux<Resume> getResumesByTemplate(String template) {
        return resumeRepository.findByTemplate(template);
    }

    @Override
    public void mapResumeFileToResume(MultipartFile resumeFile) {
        ResumeDocument resumeDocument = new ResumeDocument();
        // process Resume
        // extract sections
        // save to S3
        resumeRepository.insert(resumeDocument);
    }
}
