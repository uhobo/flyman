package com.geller.charts.repository;

import com.geller.charts.domain.Responding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Responding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespondingRepository extends MongoRepository<Responding, String> {
    @Query("{}")
    Page<Responding> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Responding> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Responding> findOneWithEagerRelationships(String id);
    
    @Query("{'surveyIds': {'Exists': [?0]}}")
    List<Responding> findAllRespondingsBySurveyId(String surveyId);

}
