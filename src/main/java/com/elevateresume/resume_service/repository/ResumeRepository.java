package com.elevateresume.resume_service.repository;

import com.elevateresume.resume_service.document.ResumeDocument;
import com.elevateresume.resume_service.dto.Resume;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResumeRepository extends ReactiveMongoRepository<ResumeDocument,String> {

    Mono<Resume> findByUserId(String userId);

    Flux<Resume> findByTemplate(String template);
}
