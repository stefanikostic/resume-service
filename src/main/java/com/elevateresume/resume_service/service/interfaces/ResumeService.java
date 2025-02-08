package com.elevateresume.resume_service.service.interfaces;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    Optional<Resume> getResumeByUserId();
    List<Resume> getResumesByTemplate(String userId);
    Resume createResume(ResumeInsertDTO resumeInsertDTO);
    void createResume(ResumeUploadDataDTO resumeUploadDataDTO, MultipartFile file);
}
