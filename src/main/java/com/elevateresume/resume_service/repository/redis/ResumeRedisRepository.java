package com.elevateresume.resume_service.repository.redis;

import com.elevateresume.resume_service.redis.model.ResumeRedisModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRedisRepository extends CrudRepository<ResumeRedisModel, String> {
    Optional<ResumeRedisModel> findByUserId(String userId);
}
