package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.redis.model.BulletPointRedisModel;
import com.elevateresume.resume_service.redis.model.ResumeRedisModel;
import com.elevateresume.resume_service.redis.model.SectionRedisModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResumeMapper {

    public ResumeRedisModel toResumeRedisModel(Resume resume) {
        List<SectionRedisModel> sectionRedisModels = resume.getSections().stream().map(s ->
                        new SectionRedisModel(s.getId(), s.getTitle(), s.getType(), s.getContent(), s.getSectionOrder(),
                                s.isOptional(), s.getBulletPoints().stream()
                                .map(b -> new BulletPointRedisModel(b.getId(), b.getTitle(), b.getContent(),
                                        b.getImprovedContent(), b.isImprovementApproved())).toList(),
                                s.getStartPeriod(), s.getEndPeriod()))
                .toList();
        return new ResumeRedisModel(resume.getId(), resume.getUserId(), resume.getCreatedAt(),
                resume.getCreatedAt(), resume.getTitle(), resume.getTemplate(), resume.getFileName(),
                sectionRedisModels, resume.getS3FileName(), resume.getS3Link());
    }
}
