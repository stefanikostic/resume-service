package com.elevateresume.resume_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("aws")
public class AwsConfigurationProperties {

    private String secretKey;
    private String accessKey;
    private S3ConfigurationProperties s3;
}
