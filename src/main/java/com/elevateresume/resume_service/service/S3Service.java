package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.config.S3ConfigurationProperties;
import com.elevateresume.resume_service.dto.ResumeUploadDataDTO;
import com.elevateresume.resume_service.exception.S3ObjectNotFoundException;
import com.elevateresume.resume_service.service.interfaces.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectVersionsResponse;
import software.amazon.awssdk.services.s3.model.ObjectVersion;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements StorageService {

    private final S3Client s3Client;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    @Override
    public ResumeUploadDataDTO uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        String bucketName = s3ConfigurationProperties.getBucketName();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        return new ResumeUploadDataDTO(fileName, fileUrl);
    }

    @Override
    public String getLatestVersionOfResume(String objectKey) throws IOException {
        String bucketName = s3ConfigurationProperties.getBucketName();
        ListObjectVersionsRequest listObjectVersionsRequest = ListObjectVersionsRequest.builder()
                .bucket(bucketName)
                .prefix(objectKey)
                .build();

        ListObjectVersionsResponse listBucketsResponse = s3Client.listObjectVersions(listObjectVersionsRequest);

        List<ObjectVersion> versions = listBucketsResponse.versions();

        ObjectVersion latestVersion = versions.stream()
                .max(Comparator.comparing(ObjectVersion::lastModified)).orElseThrow(S3ObjectNotFoundException::new);
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(GetObjectRequest.builder().bucket(bucketName)
                .key(objectKey)
                .versionId(latestVersion.versionId()).build());
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }
}
