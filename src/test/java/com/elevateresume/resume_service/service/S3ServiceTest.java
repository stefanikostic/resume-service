package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.config.S3ConfigurationProperties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @InjectMocks
    private S3Service s3Service;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3ConfigurationProperties s3ConfigurationProperties;

    @SneakyThrows
    @Test
    void uploadFile_s3ObjectIsUploadedSuccessfully() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("fileName");
        byte[] bytes = new byte[1];
        when(file.getBytes()).thenReturn(bytes);
        when(s3ConfigurationProperties.getBucketName()).thenReturn("bucketName");

        // when
        var resumeUploadData = s3Service.uploadFile(file);

        // then
        verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
        assertThat(resumeUploadData.fileName()).contains("fileName");
        assertThat(resumeUploadData.fileUrl()).isEqualTo("https://bucketName.s3.amazonaws.com/" + resumeUploadData.fileName());
    }

}