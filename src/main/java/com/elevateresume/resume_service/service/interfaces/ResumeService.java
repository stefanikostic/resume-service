package com.elevateresume.resume_service.service.interfaces;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    ResumeDTO getResumeByUserId(String userId);
    ResumeDTO getResumeFromDb(String userId);
    List<Resume> getResumesByTemplate(String userId);
    Resume createResume(ResumeInsertDTO resumeInsertDTO);
    ResumeDTO createResume(ResumeUploadDataDTO resumeUploadDataDTO, MultipartFile file);
}
