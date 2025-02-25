package com.elevateresume.resume_service.repository.jpa;

import com.elevateresume.resume_service.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeJpaRepository extends JpaRepository<Resume, String> {

    Optional<Resume> findByUserId(String userId);

    List<Resume> findByTemplate(String template);
}
