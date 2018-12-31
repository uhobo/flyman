package com.geller.charts.service;

import com.geller.charts.domain.AnswerScale;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AnswerScale.
 */
public interface AnswerScaleService {

    /**
     * Save a answerScale.
     *
     * @param answerScale the entity to save
     * @return the persisted entity
     */
    AnswerScale save(AnswerScale answerScale);

    /**
     * Get all the answerScales.
     *
     * @return the list of entities
     */
    List<AnswerScale> findAll();


    /**
     * Get the "id" answerScale.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AnswerScale> findOne(String id);

    /**
     * Delete the "id" answerScale.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
