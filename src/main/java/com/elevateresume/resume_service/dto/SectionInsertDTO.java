package com.elevateresume.resume_service.dto;

import com.elevateresume.resume_service.entity.SectionType;

import java.util.List;

public record SectionInsertDTO(SectionType sectionType, String title, String startPeriod, String endPeriod, String content,
                               List<BulletPointInsertDTO> bulletPoints) {
}
