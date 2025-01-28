package com.elevateresume.resume_service.controller;

import com.elevateresume.resume_service.dto.Resume;
import com.elevateresume.resume_service.repository.ResumeRepository;
import com.elevateresume.resume_service.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<Resume>> getResumeByUserId(@PathVariable String userId) {
        return resumeService.getResumeByUserId(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public Flux<ResponseEntity<Resume>> getResumesByTemplate() {
        return resumeService.getResumesByTemplate("template")
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> uploadResume(@RequestParam("resume") MultipartFile file) {
        resumeService.mapResumeFileToResume(file);
        return ResponseEntity.ok("Success");
    }
}
