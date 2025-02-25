package com.elevateresume.resume_service.controller;

import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.util.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/resume")
@RequiredArgsConstructor
public class ResumeInternalController {

    private final ResumeService resumeService;
    private final RequestContext requestContext;

    @GetMapping("/user")
    public ResponseEntity<ResumeDTO> getResumeFromDb() {
        String userId = requestContext.getUserId();
        ResumeDTO resumeDTO = resumeService.getResumeFromDb(userId);
        if (resumeDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resumeDTO);
    }
}
