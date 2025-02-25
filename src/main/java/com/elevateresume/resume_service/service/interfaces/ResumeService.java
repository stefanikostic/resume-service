package com.elevateresume.resume_service.service.interfaces;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.redis.model.ResumeRedisModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    ResumeRedisModel getResumeByUserId(String userId);
    ResumeRedisModel getResumeFromDb(String userId);
    List<Resume> getResumesByTemplate(String userId);
    Resume createResume(ResumeInsertDTO resumeInsertDTO);
    ResumeRedisModel createResume(ResumeUploadDataDTO resumeUploadDataDTO, MultipartFile file);
}
