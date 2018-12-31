package com.geller.charts.service;

import com.geller.charts.domain.Survey;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Survey.
 */
public interface SurveyService {

    /**
     * Save a survey.
     *
     * @param survey the entity to save
     * @return the persisted entity
     */
    Survey save(Survey survey);

    /**
     * Get all the surveys.
     *
     * @return the list of entities
     */
    List<Survey> findAll();
    
    
    List<Survey> findAllAreNotIn(List<String> ids);
    /**
     * Get all the Survey with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Survey> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" survey.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Survey> findOne(String id);

    /**
     * Delete the "id" survey.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
