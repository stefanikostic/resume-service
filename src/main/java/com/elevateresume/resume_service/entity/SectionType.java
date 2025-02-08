package com.elevateresume.resume_service.entity;

import lombok.Getter;

@Getter
public enum SectionType {
    DEFAULT(0, true),
    WORK_EXPERIENCE(1, false),
    EDUCATION(2, false),
    SKILLS(3, true),
    PROJECTS(4, true),
    CERTIFICATIONS(5, true);

    private final int order;
    private final boolean isOptional;

    SectionType(int order, boolean isOptional) {
        this.order = order;
        this.isOptional = isOptional;
    }
}
