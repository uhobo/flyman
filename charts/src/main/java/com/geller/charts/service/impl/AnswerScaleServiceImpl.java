package com.geller.charts.service.impl;

import com.geller.charts.service.AnswerScaleService;
import com.geller.charts.domain.AnswerScale;
import com.geller.charts.repository.AnswerScaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing AnswerScale.
 */
@Service
public class AnswerScaleServiceImpl implements AnswerScaleService {

    private final Logger log = LoggerFactory.getLogger(AnswerScaleServiceImpl.class);

    private AnswerScaleRepository answerScaleRepository;

    public AnswerScaleServiceImpl(AnswerScaleRepository answerScaleRepository) {
        this.answerScaleRepository = answerScaleRepository;
    }

    /**
     * Save a answerScale.
     *
     * @param answerScale the entity to save
     * @return the persisted entity
     */
    @Override
    public AnswerScale save(AnswerScale answerScale) {
        log.debug("Request to save AnswerScale : {}", answerScale);
        return answerScaleRepository.save(answerScale);
    }

    /**
     * Get all the answerScales.
     *
     * @return the list of entities
     */
    @Override
    public List<AnswerScale> findAll() {
        log.debug("Request to get all AnswerScales");
        return answerScaleRepository.findAll();
    }


    /**
     * Get one answerScale by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<AnswerScale> findOne(String id) {
        log.debug("Request to get AnswerScale : {}", id);
        return answerScaleRepository.findById(id);
    }

    /**
     * Delete the answerScale by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AnswerScale : {}", id);
        answerScaleRepository.deleteById(id);
    }
}
