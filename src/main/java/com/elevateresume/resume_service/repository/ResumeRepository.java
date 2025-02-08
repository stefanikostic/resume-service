package com.elevateresume.resume_service.repository;

import com.elevateresume.resume_service.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, String> {

    Optional<Resume> findByUserId(String userId);

    List<Resume> findByTemplate(String template);
}
