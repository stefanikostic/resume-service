package com.elevateresume.resume_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("aws.s3")
public class S3ConfigurationProperties {

    private String region;
    private String bucketName;
}
