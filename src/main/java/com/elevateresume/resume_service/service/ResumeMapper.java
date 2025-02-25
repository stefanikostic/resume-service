package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.redis.model.BulletPointDTO;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.redis.model.SectionDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResumeMapper {

    public ResumeDTO toResumeRedisModel(Resume resume) {
        List<SectionDTO> sectionDTOS = resume.getSections().stream().map(s ->
                        new SectionDTO(s.getId(), s.getTitle(), s.getType(), s.getContent(), s.getSectionOrder(),
                                s.isOptional(), s.getBulletPoints().stream()
                                .map(b -> new BulletPointDTO(b.getId(), b.getTitle(), b.getContent(),
                                        b.getImprovedContent(), b.isImprovementApproved())).toList(),
                                s.getStartPeriod(), s.getEndPeriod()))
                .toList();
        return new ResumeDTO(resume.getId(), resume.getUserId(), resume.getCreatedAt(),
                resume.getCreatedAt(), resume.getTitle(), resume.getTemplate(), resume.getFileName(),
                sectionDTOS, resume.getS3FileName(), resume.getS3Link());
    }
}
