package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.BulletPointDTO;
import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.dto.SectionDTO;
import com.elevateresume.resume_service.entity.BulletPoint;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.entity.Section;
import com.elevateresume.resume_service.repository.ResumeRepository;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.util.RequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final RequestContext requestContext;

    @Override
    public Optional<Resume> getResumeByUserId() {
        String userId = requestContext.getUserId();
        return resumeRepository.findByUserId(userId);
    }

    @Override
    public List<Resume> getResumesByTemplate(String template) {
        return resumeRepository.findByTemplate(template);
    }

    @Override
    public Resume createResume(ResumeInsertDTO resumeInsertDTO) {
        Resume resume = new Resume();
        resume.setCreatedAt(LocalDateTime.now());
        resume.setTitle(resumeInsertDTO.title());
        for (SectionDTO sectionDTO : resumeInsertDTO.sections()) {
            Section section = new Section();
            section.setTitle(sectionDTO.title());
            section.setContent(sectionDTO.content());
            section.setStartPeriod(sectionDTO.startPeriod());
            section.setEndPeriod(sectionDTO.endPeriod());
            section.setType(sectionDTO.sectionType());
            List<BulletPoint> bulletPoints = new ArrayList<>();
            for (BulletPointDTO bulletPointDTO : sectionDTO.bulletPoints()) {
                BulletPoint bulletPoint = new BulletPoint();
                bulletPoint.setTitle(bulletPointDTO.title());
                bulletPoint.setContent(bulletPointDTO.content());
                bulletPoints.add(bulletPoint);
            }
            section.setBulletPoints(bulletPoints);

            resume.addSection(section);
        }

        return resumeRepository.save(resume);
    }

    @Override
    public void createResume(ResumeUploadDataDTO resumeUploadDataDTO, MultipartFile file) {
        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setS3Link(resumeUploadDataDTO.fileUrl());
        resume.setS3FileName(resumeUploadDataDTO.fileName());

        resume.setUserId(requestContext.getUserId());

        resumeRepository.save(resume);
    }
}
