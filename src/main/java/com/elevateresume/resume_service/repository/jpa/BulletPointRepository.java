package com.elevateresume.resume_service.repository.jpa;

import com.elevateresume.resume_service.entity.BulletPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletPointRepository extends JpaRepository<BulletPoint, Long> {
}
