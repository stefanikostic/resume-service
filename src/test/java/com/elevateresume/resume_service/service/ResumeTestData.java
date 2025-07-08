package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.BulletPointInsertDTO;
import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.dto.SectionInsertDTO;
import com.elevateresume.resume_service.entity.BulletPoint;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.entity.ResumeTemplate;
import com.elevateresume.resume_service.entity.Section;
import com.elevateresume.resume_service.entity.SectionType;
import com.elevateresume.resume_service.redis.model.BulletPointDTO;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.redis.model.SectionDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@UtilityClass
public class ResumeTestData {

    public ResumeDTO buildResumeDTO() {
        BulletPointDTO bulletPointDTO = new BulletPointDTO("bulletPointId", "title", "content", "improvedContent",
                false);
        SectionDTO sectionDTO = new SectionDTO("sectionId", "sectionTitle", SectionType.DEFAULT,
                "section content", 1, true, List.of(bulletPointDTO), "04.04.2020", "04.04.2025");
        return new ResumeDTO("resumeId", "userId", LocalDateTime.of(2025, Month.JUNE, 24, 10, 40, 0),
                LocalDateTime.of(2025, Month.JUNE, 24, 10, 40, 0), "Resume title", ResumeTemplate.ORIGINAL,
                "ResumeFileName", List.of(sectionDTO), "s3FileName", "s3Link");
    }

    public Resume buildResume() {
        Resume resume = new Resume();
        resume.setUserId("userId");
        resume.setTemplate(ResumeTemplate.ORIGINAL);
        resume.setTitle("title");
        resume.setS3FileName("s3FileName");
        resume.setS3Link("s3Link");
        resume.setFileName("fileName");
        resume.setCreatedAt(LocalDateTime.of(2025, Month.JUNE, 24, 10, 40, 0));
        Section section = new Section();
        BulletPoint bulletPoint = new BulletPoint();
        bulletPoint.setContent("content");
        bulletPoint.setTitle("title");
        section.setType(SectionType.DEFAULT);
        resume.setSections(List.of(section));
        return resume;
    }

    public ResumeInsertDTO buildResumeInsertDTO() {
        BulletPointInsertDTO bulletPointInsertDTO = new BulletPointInsertDTO("title", "content");
        SectionInsertDTO sectionInsertDTO = new SectionInsertDTO(SectionType.WORK_EXPERIENCE, "Work Experience",
                "23-06-2020", "23-06-2025", "content", List.of(bulletPointInsertDTO));
        return new ResumeInsertDTO("ResumeTitle", List.of(sectionInsertDTO));
    }

    public ResumeUploadDataDTO buildResumeUploadDataDTO() {
        return new ResumeUploadDataDTO("resumeName", "resumeUrl");
    }
}
