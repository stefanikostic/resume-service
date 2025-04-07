package com.elevateresume.resume_service.mapper;

import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.SectionInsertDTO;
import com.elevateresume.resume_service.entity.Resume;
import com.elevateresume.resume_service.entity.Section;
import com.elevateresume.resume_service.redis.model.ResumeDTO;
import com.elevateresume.resume_service.redis.model.SectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeDTO toResumeDTO(Resume resume);

    @Mapping(target = "sections", ignore = true)
    Resume toResume(ResumeInsertDTO resumeInsertDTO);

    @Mapping(target = "resume", ignore = true)
    Section toSection(SectionInsertDTO request);

    default Resume mapResumeWithSections(ResumeInsertDTO resumeInsertDTO) {
        Resume resume = toResume(resumeInsertDTO);
        resume.setSections(
                resumeInsertDTO.sections().stream()
                        .map(sectionRequest -> {
                            Section section = toSection(sectionRequest);
                            section.setResume(resume);
                            return section;
                        })
                        .collect(Collectors.toList())
        );
        return resume;
    }
}
