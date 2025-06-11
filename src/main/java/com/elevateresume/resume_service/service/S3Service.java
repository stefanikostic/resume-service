package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.config.S3ConfigurationProperties;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.service.interfaces.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements StorageService {

    private static final String AMAZON_S3 = ".s3.amazonaws.com/";
    private static final String DASH_SYMBOL = "-";
    private static final String HTTPS = "https://";
    private final S3Client s3Client;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    @Override
    public ResumeUploadDataDTO uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + DASH_SYMBOL + file.getOriginalFilename();

        String bucketName = s3ConfigurationProperties.getBucketName();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        String fileUrl = HTTPS + bucketName + AMAZON_S3 + fileName;
        return new ResumeUploadDataDTO(fileName, fileUrl);
    }
}
