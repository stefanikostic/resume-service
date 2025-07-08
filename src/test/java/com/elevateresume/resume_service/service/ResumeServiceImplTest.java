package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.entity.ResumeTemplate;
import com.elevateresume.resume_service.mapper.ResumeMapper;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.repository.jpa.ResumeJpaRepository;
import com.elevateresume.resume_service.repository.redis.ResumeRedisRepository;
import com.elevateresume.resume_service.util.RequestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResumeServiceImplTest {

    @Mock
    ResumeJpaRepository resumeJpaRepository;

    @Mock
    ResumeRedisRepository resumeRedisRepository;

    @Mock
    RequestContext requestContext;

    @Mock
    ResumeProcessor resumeProcessor;

    @Mock
    ResumeMapper resumeMapper;

    @InjectMocks
    ResumeServiceImpl resumeService;

    @Test
    void getResumeByUserId_userIsObtainedFromDb() {
        // given
        String userId = "testUserId";
        Resume resume = mock(Resume.class);
        when(resumeJpaRepository.findByUserId(any())).thenReturn(Optional.of(resume));
        ResumeDTO resumeDTO = ResumeTestData.buildResumeDTO();

        when(resumeMapper.toResumeDTO(any())).thenReturn(resumeDTO);

        // when
        ResumeDTO result = resumeService.getResumeByUserId(userId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(resumeDTO);
    }

    @Test
    void getResumeByUserId_userIsObtainedFromRedis() {
        // given
        String userId = "testUserId";

        ResumeDTO resumeDTO = ResumeTestData.buildResumeDTO();
        when(resumeRedisRepository.findByUserId(any())).thenReturn(Optional.of(resumeDTO));

        // when
        ResumeDTO result = resumeService.getResumeByUserId(userId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(resumeDTO);
    }

    @Test
    void getResumeFromDb_resumeExists() {
        // given
        String userId = "testUserId";
        Resume resume = mock(Resume.class);
        when(resumeJpaRepository.findByUserId(eq(userId))).thenReturn(Optional.of(resume));
        ResumeDTO resumeDTO = ResumeTestData.buildResumeDTO();

        when(resumeMapper.toResumeDTO(resume)).thenReturn(resumeDTO);

        // when
        ResumeDTO result = resumeService.getResumeFromDb(userId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(resumeDTO);
    }

    @Test
    void getResumeFromDb_resumeDoesNotExist() {
        // given
        String userId = "testUserId";

        // when
        ResumeDTO result = resumeService.getResumeFromDb(userId);

        // then
        assertThat(result).isNull();
    }

    @Test
    void getResumeByTemplate_resumeExists() {
        // given
        String template = "ORIGINAL";
        Resume resume = ResumeTestData.buildResume();
        when(resumeJpaRepository.findByTemplate(template)).thenReturn(List.of(resume));

        // when
        List<Resume> result = resumeService.getResumesByTemplate(template);

        // then
        assertThat(result).hasSize(1);
        Resume resumeResult = result.getFirst();
        assertThat(resumeResult).usingRecursiveComparison().isEqualTo(resume);
    }

    @Test
    void createResume_ResumeIsCreatedSuccessfully() {
        // given
        ResumeInsertDTO resumeInsertDTO = ResumeTestData.buildResumeInsertDTO();
        when(resumeProcessor.processResume()).thenReturn(resumeInsertDTO);
        Resume resume = ResumeTestData.buildResume();
        when(resumeMapper.toResume(resumeInsertDTO)).thenReturn(resume);
        String userId = "userId";
        when(requestContext.getUserId()).thenReturn(userId);
        when(resumeJpaRepository.save(any())).thenReturn(resume);
        when(resumeMapper.toResumeDTO(any())).thenReturn(ResumeTestData.buildResumeDTO());
        ResumeUploadDataDTO resumeUploadDataDTO = new ResumeUploadDataDTO("ResumeFileName", "fileUrl");
        MultipartFile file = mock(MultipartFile.class);

        // when
        ResumeDTO resumeDTO = resumeService.createResume(resumeUploadDataDTO, file);

        // then
        verify(resumeJpaRepository).save(any());
        verify(resumeRedisRepository).save(any());
        assertThat(resumeDTO.resumeTemplate()).isEqualTo(ResumeTemplate.ORIGINAL);
        assertThat(resumeDTO.fileName()).isEqualTo("ResumeFileName");
        assertThat(resumeDTO.userId()).isEqualTo("userId");
        assertThat(resumeDTO.s3FileName()).isEqualTo("s3FileName");
        assertThat(resumeDTO.title()).isEqualTo("Resume title");
        assertThat(resumeDTO.s3Link()).isEqualTo("s3Link");
    }

    @Test
    void createResumeWithResumeInsertDTO_ResumeIsCreatedSuccessfully() {
        // given
        ResumeInsertDTO resumeInsertDTO = ResumeTestData.buildResumeInsertDTO();

        // when
        Resume result = resumeService.createResume(resumeInsertDTO);

        // then
        verify(resumeJpaRepository).save(any());
    }
}