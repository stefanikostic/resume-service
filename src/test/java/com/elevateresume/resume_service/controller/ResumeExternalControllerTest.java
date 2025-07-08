package com.elevateresume.resume_service.controller;

import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.jwt.JwtDataExtractor;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.service.ResumeTestData;
import com.elevateresume.resume_service.service.interfaces.ResumeService;
import com.elevateresume.resume_service.service.interfaces.StorageService;
import com.elevateresume.resume_service.util.RequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ResumeExternalControllerTest {

    private static final String EXTERNAL_RESUME_URL = "/external/resume/";
    private static final String USER_ID = "userId";
    private static final String EXTERNAL_RESUME_UPLOAD = "/external/resume/upload";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResumeService resumeService;

    @MockBean
    private RequestContext requestContext;

    @MockBean
    private StorageService storageService;

    @MockBean
    private JwtDataExtractor jwtDataExtractor;

    @SneakyThrows
    @Test
    void getResumeByUserId_returnsOk() {
        // given
        ResumeDTO resumeDTO = ResumeTestData.buildResumeDTO();
        when(resumeService.getResumeByUserId(any())).thenReturn(resumeDTO);
        when(requestContext.getUserId()).thenReturn(USER_ID);

        // when
        MvcResult mvcResult = mockMvc.perform(get(EXTERNAL_RESUME_URL))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        var resultString = response.getContentAsString();

        // then
        assertThat(resultString).isNotNull();
        var result = objectMapper.readValue(resultString, ResumeDTO.class);
        assertThat(result).usingRecursiveComparison().isEqualTo(resumeDTO);
    }

    @SneakyThrows
    @Test
    void getResumeByUserId_returnsNotFound() {
        // given
        when(requestContext.getUserId()).thenReturn(USER_ID);

        // when
        // then
        mockMvc.perform(get(EXTERNAL_RESUME_URL))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @SneakyThrows
    @Test
    void uploadResume_resumeIsSavedSuccessfully() {
        // given
        byte[] fileContent = "This is a dummy resume content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "resume",
                "resume.pdf",
                "application/pdf",
                fileContent
        );
        ResumeUploadDataDTO resumeUploadDataDTO = ResumeTestData.buildResumeUploadDataDTO();
        when(storageService.uploadFile(any())).thenReturn(resumeUploadDataDTO);

        // when
        // then
        mockMvc.perform(
                        multipart(EXTERNAL_RESUME_UPLOAD)
                                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Resume uploaded successfully: " + resumeUploadDataDTO));

        verify(resumeService).createResume(eq(resumeUploadDataDTO), eq(file));
    }
}