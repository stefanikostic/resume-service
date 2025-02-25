package com.elevateresume.resume_service.repository.redis;

import com.elevateresume.resume_service.redis.model.ResumeDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRedisRepository extends CrudRepository<ResumeDTO, String> {
    Optional<ResumeDTO> findByUserId(String userId);
}
