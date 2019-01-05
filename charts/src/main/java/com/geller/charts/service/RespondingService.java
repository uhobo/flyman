package com.geller.charts.service;

import com.geller.charts.domain.Responding;
import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.SurveyResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing Responding.
 */
public interface RespondingService {

    /**
     * Save a responding.
     *
     * @param responding the entity to save
     * @return the persisted entity
     */
    Responding save(Responding responding);

    /**
     * Get all the respondings.
     *
     * @return the list of entities
     */
    List<Responding> findAll();

    /**
     * Get all the Responding with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Responding> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" responding.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Responding> findOne(String id);

    /**
     * Delete the "id" responding.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    List<Responding> getRespondingBySurvey(String surveyId);
}
