package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.mapper.ResumeMapper;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.repository.jpa.ResumeJpaRepository;
import com.elevateresume.resume_service.repository.redis.ResumeRedisRepository;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.util.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;
    private final ResumeRedisRepository resumeRedisRepository;
    private final RequestContext requestContext;
    private final ResumeProcessor resumeProcessor;
    private final ResumeMapper resumeMapper;

    @Override
    public ResumeDTO getResumeByUserId(String userId) {
        log.info("Started get resume.");
        Optional<ResumeDTO> resumeRedisModel = resumeRedisRepository.findByUserId(userId);
        if (resumeRedisModel.isPresent()) {
            log.debug("Resume fetched from cache.");
            return resumeRedisModel.get();
        }

        log.debug("Resume fetched from DB.");
        return getResumeFromDb(userId);
    }

    @Override
    @Transactional
    public ResumeDTO getResumeFromDb(String userId) {
        Optional<Resume> resume = resumeJpaRepository.findByUserId(userId);
        return resume.map(resumeMapper::toResumeDTO).orElse(null);
    }

    @Override
    public List<Resume> getResumesByTemplate(String template) {
        return resumeJpaRepository.findByTemplate(template);
    }

    @Override
    @Transactional
    public Resume createResume(ResumeInsertDTO resumeInsertDTO) {
        Resume resume = new Resume();

        return resumeJpaRepository.save(resume);
    }

    @Override
    @Transactional
    public ResumeDTO createResume(ResumeUploadDataDTO resumeUploadDataDTO, MultipartFile file) {
        ResumeInsertDTO resumeInsertDTO = resumeProcessor.processResume();
        Resume resume = resumeMapper.toResume(resumeInsertDTO);

        resume.setFileName(file.getOriginalFilename());
        resume.setS3Link(resumeUploadDataDTO.fileUrl());
        resume.setS3FileName(resumeUploadDataDTO.fileName());
        resume.setCreatedAt(LocalDateTime.now());
        resume.setUserId(requestContext.getUserId());

        Resume savedResume = save(resume);
        log.debug("Resume persisted in DB.");

        ResumeDTO resumeDTO = resumeMapper.toResumeDTO(savedResume);
        resumeRedisRepository.save(resumeDTO);
        log.debug("Resume persisted in cache.");

        return resumeDTO;
    }

    private Resume save(Resume resume) {
        return resumeJpaRepository.save(resume);
    }
}
