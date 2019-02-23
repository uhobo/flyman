package com.geller.charts.repository;

import com.geller.charts.domain.SurveyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the SurveyResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveyResultRepository extends MongoRepository<SurveyResult, String> {
    @Query("{}")
    Page<SurveyResult> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<SurveyResult> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<SurveyResult> findOneWithEagerRelationships(String id);
    
    @Query("{'surveyId': ?0, 'respondingId': ?1}")
    List<SurveyResult> findBySurveyAndReponsing(String surveyId, String respongingId);
    
    @Query("{'surveyId': ?0}")
    List<SurveyResult> findBySurvey(String surveyId);
    
    
    @Query("{'respondingId': ?0}")
    List<SurveyResult> findByResponding(String respongingId);
    
}
