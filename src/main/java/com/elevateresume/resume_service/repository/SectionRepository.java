package com.elevateresume.resume_service.repository;

import com.elevateresume.resume_service.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
