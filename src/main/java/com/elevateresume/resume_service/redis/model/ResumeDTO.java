package com.elevateresume.resume_service.redis.model;

import com.elevateresume.resume_service.entity.ResumeTemplate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@RedisHash("Resume")
public record ResumeDTO(String id, @Indexed String userId, LocalDateTime createdAt,
                        LocalDateTime updatedAt, String title, ResumeTemplate resumeTemplate,
                        String fileName, List<SectionDTO> sections, String s3FileName, String s3Link) {
}