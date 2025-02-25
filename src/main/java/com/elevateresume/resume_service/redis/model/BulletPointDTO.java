package com.elevateresume.resume_service.redis.model;

public record BulletPointDTO(String id, String title, String content, String improvedContent,
                             boolean isImprovementApproved) {
}