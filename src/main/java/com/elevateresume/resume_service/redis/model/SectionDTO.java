package com.elevateresume.resume_service.redis.model;

import com.elevateresume.resume_service.entity.SectionType;

import java.util.List;

public record SectionDTO(String id, String title, SectionType type, String content, Integer sectionOrder, boolean isOptional,
                         List<BulletPointDTO> bulletPointModelList, String startPeriod, String endPeriod) {
}