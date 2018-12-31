package com.geller.charts.service;

import com.geller.charts.domain.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Question.
 */
public interface QuestionService {

    /**
     * Save a question.
     *
     * @param question the entity to save
     * @return the persisted entity
     */
    Question save(Question question);

    /**
     * Get all the questions.
     *
     * @return the list of entities
     */
    List<Question> findAll();

    /**
     * Get all the Question with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Question> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" question.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Question> findOne(String id);

    /**
     * Delete the "id" question.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
