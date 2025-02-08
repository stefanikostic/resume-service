package com.elevateresume.resume_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfig {

    private final AwsConfigurationProperties awsConfigurationProperties;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(s3ConfigurationProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsConfigurationProperties.getAccessKey(),
                                awsConfigurationProperties.getSecretKey())))
                .build();
    }
}
