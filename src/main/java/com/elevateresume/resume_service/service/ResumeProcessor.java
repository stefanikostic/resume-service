package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeProcessor {

    private final PdfParserService pdfParserService;
    private final ObjectMapper objectMapper;

    public ResumeInsertDTO processResume(MultipartFile resumeFile) throws IOException {
        String resumeContent = pdfParserService.fetchPdfContent(resumeFile);;
        // extract sections
        // deepSeekService.extractResumeSections();
        // process Resume

        // save to S3
        return objectMapper.readValue(resumeContent, ResumeInsertDTO.class);
    }

    /*
    *  @Override
    public void mapResumeFileToResume(MultipartFile resumeFile) {
        Resume resume = new Resume();
//        deepSeekService.extractResumeSections();
        // process Resume
        // extract sections
        // save to S3
        resumeRepository.save(resume);
    }*/
}
