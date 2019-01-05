package com.geller.charts.service;

import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.SurveyResult;
import com.geller.charts.domain.menus.MenuItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SurveyResult.
 */
public interface SurveyResultService {

    /**
     * Save a surveyResult.
     *
     * @param surveyResult the entity to save
     * @return the persisted entity
     */
    SurveyResult save(SurveyResult surveyResult);

    /**
     * Get all the surveyResults.
     *
     * @return the list of entities
     */
    List<SurveyResult> findAll();

    /**
     * Get all the SurveyResult with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<SurveyResult> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" surveyResult.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SurveyResult> findOne(String id);

    /**
     * Delete the "id" surveyResult.
     *
     * @param id the id of the entity
     */
    void delete(String id);
    
    List<SurveyResult> findByRespondingId(String respondingId);
    
    List<SurveyResult> findBySurveyAndResponding(String surveyId, String respondingId);

	List<SurveyResult> publish(RespondingSurveyInput respondingSurveyInput);
	
	List<MenuItem> getSurveyMenu();
}
