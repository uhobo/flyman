package com.geller.charts.repository;

import com.geller.charts.domain.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Survey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {
    @Query("{}")
    Page<Survey> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Survey> findAllWithEagerRelationships();

    @Query("{'id': ?0}")  
    Optional<Survey> findOneWithEagerRelationships(String id);

    //{_id: {$nin: ["5be204ece3a24d2d4847b921"]}}
    @Query("{'id': {'$nin': ?0} }")
    List<Survey> findAllNotInList(List<String> ids);
}
