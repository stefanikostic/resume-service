package com.elevateresume.resume_service.redis.model;

import com.elevateresume.resume_service.entity.SectionType;

import java.util.List;

public record SectionRedisModel(String id, String title, SectionType type, String content, Integer sectionOrder, boolean isOptional,
                                List<BulletPointRedisModel> bulletPointModelList, String startPeriod, String endPeriod) {
}