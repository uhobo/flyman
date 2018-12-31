package com.geller.charts.service.impl;

import com.geller.charts.service.SurveyService;
import com.geller.charts.domain.Survey;
import com.geller.charts.repository.SurveyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Survey.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    private final Logger log = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Save a survey.
     *
     * @param survey the entity to save
     * @return the persisted entity
     */
    @Override
    public Survey save(Survey survey) {
        log.debug("Request to save Survey : {}", survey);
        return surveyRepository.save(survey);
    }

    /**
     * Get all the surveys.
     *
     * @return the list of entities
     */
    @Override
    public List<Survey> findAll() {
        log.debug("Request to get all Surveys");
        return surveyRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Survey with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Survey> findAllWithEagerRelationships(Pageable pageable) {
        return surveyRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one survey by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Survey> findOne(String id) {
        log.debug("Request to get Survey : {}", id);
        return surveyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the survey by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Survey : {}", id);
        surveyRepository.deleteById(id);
    }

	@Override
	public List<Survey> findAllAreNotIn(List<String> ids) {
		log.debug("Find all Survey  are not : {}", ids);
		return surveyRepository.findAllNotInList(ids);
	}
}
