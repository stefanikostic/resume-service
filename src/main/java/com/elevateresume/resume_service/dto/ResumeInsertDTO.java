package com.elevateresume.resume_service.dto;

import java.util.List;

public record ResumeInsertDTO(String title, List<SectionDTO> sections) {
}
