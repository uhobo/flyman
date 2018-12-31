package com.geller.charts.repository;

import com.geller.charts.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    @Query("{}")
    Page<Question> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Question> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Question> findOneWithEagerRelationships(String id);

}
