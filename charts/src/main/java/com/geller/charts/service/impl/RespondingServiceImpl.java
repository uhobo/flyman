package com.geller.charts.service.impl;

import com.geller.charts.service.AskedService;
import com.geller.charts.service.RespondingService;
import com.geller.charts.service.SurveyResultService;
import com.geller.charts.service.SurveyService;
import com.geller.charts.domain.Asked;
import com.geller.charts.domain.AskedResult;
import com.geller.charts.domain.Responding;
import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.Survey;
import com.geller.charts.domain.SurveyResult;
import com.geller.charts.repository.RespondingRepository;
import com.geller.charts.repository.SurveyResultRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Responding.
 */
@Service
public class RespondingServiceImpl implements RespondingService {

    private final Logger log = LoggerFactory.getLogger(RespondingServiceImpl.class);

    private RespondingRepository respondingRepository;
    
    @Autowired
    private SurveyService surveyService;

    //private SurveyResultService surveyResultService;
    
   // private AskedService askedService;
    
    
    
    public RespondingServiceImpl(RespondingRepository respondingRepository) {
        this.respondingRepository = respondingRepository;
    }

    /**
     * Save a responding.
     *
     * @param responding the entity to save
     * @return the persisted entity
     */
    @Override
    public Responding save(Responding responding) {
        log.debug("Request to save Responding : {}", responding);
        responding.getSurveys().stream().forEach(survey -> {responding.getSurveyIds().add(survey.getId());});
        return respondingRepository.save(responding);
    }

    /**
     * Get all the respondings.
     *
     * @return the list of entities
     */
    @Override
    public List<Responding> findAll() {
        log.debug("Request to get all Respondings");
        List<Responding> list = respondingRepository.findAllWithEagerRelationships();
        
        list.stream().forEach(resp -> { resp.getSurveyIds().stream().forEach(id -> {resp.getSurveys().add(this.surveyService.findOne(id).get()); });  });
        return list;
       // return respondingRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Responding with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Responding> findAllWithEagerRelationships(Pageable pageable) {
        return respondingRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one responding by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Responding> findOne(String id) {
        log.debug("Request to get Responding : {}", id);
        return respondingRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the responding by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Responding : {}", id);
        respondingRepository.deleteById(id);
    }
    
    public List<Responding> getRespondingBySurvey(String surveyId){
    	return respondingRepository.findAllRespondingsBySurveyId(surveyId);
    }

	public SurveyService getSurveyService() {
		return surveyService;
	}

	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	
}
