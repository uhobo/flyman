package com.geller.charts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.SurveyResult;
import com.geller.charts.domain.charts.ChartDataWrapper;
import com.geller.charts.domain.treetable.TreeTableData;
import com.geller.charts.service.SurveyResultService;
import com.geller.charts.web.rest.errors.BadRequestAlertException;
import com.geller.charts.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing SurveyResult.
 */
@RestController
@RequestMapping("/api")
public class SurveyResultResource {

    private final Logger log = LoggerFactory.getLogger(SurveyResultResource.class);

    private static final String ENTITY_NAME = "surveyResult";

    private SurveyResultService surveyResultService;

    public SurveyResultResource(SurveyResultService surveyResultService) {
        this.surveyResultService = surveyResultService;
    }

    /**
     * POST  /survey-results : Create a new surveyResult.
     *
     * @param surveyResult the surveyResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new surveyResult, or with status 400 (Bad Request) if the surveyResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/survey-results")
    @Timed
    public ResponseEntity<SurveyResult> createSurveyResult(@RequestBody SurveyResult surveyResult) throws URISyntaxException {
        log.debug("REST request to save SurveyResult : {}", surveyResult);
        if (surveyResult.getId() != null) {
            throw new BadRequestAlertException("A new surveyResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveyResult result = surveyResultService.save(surveyResult);
        return ResponseEntity.created(new URI("/api/survey-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /survey-results : Updates an existing surveyResult.
     *
     * @param surveyResult the surveyResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated surveyResult,
     * or with status 400 (Bad Request) if the surveyResult is not valid,
     * or with status 500 (Internal Server Error) if the surveyResult couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/survey-results")
    @Timed
    public ResponseEntity<SurveyResult[]> updateSurveyResult(@RequestBody SurveyResult surveyResults[]) throws URISyntaxException {
        //wwwwwwlog.debug("REST request to update SurveyResult : ", surveyResults);
        for(SurveyResult surveyResult:  surveyResults) {
        	if (surveyResult.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        	}
        	surveyResultService.save(surveyResult);
        }
        
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(surveyResults);
    }

    /**
     * GET  /survey-results : get all the surveyResults.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of surveyResults in body
     */
    @GetMapping("/survey-results")
    @Timed
    public List<SurveyResult> getAllSurveyResults(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SurveyResults");
        return surveyResultService.findAll();
    }

    /**
     * GET  /survey-results/:id : get the "id" surveyResult.
     *
     * @param id the id of the surveyResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the surveyResult, or with status 404 (Not Found)
     */
    @GetMapping("/survey-results/{id}")
    @Timed
    public ResponseEntity<SurveyResult> getSurveyResult(@PathVariable String id) {
        log.debug("REST request to get SurveyResult : {}", id);
        Optional<SurveyResult> surveyResult = surveyResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveyResult);
    }

    /**
     * DELETE  /survey-results/:id : delete the "id" surveyResult.
     *
     * @param id the id of the surveyResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/survey-results/{id}")
    @Timed
    public ResponseEntity<Void> deleteSurveyResult(@PathVariable String id) {
        log.debug("REST request to delete SurveyResult : {}", id);
        surveyResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    
    @GetMapping("/survey-results/getSurveyResultByResponding/{id}")
    @Timed
    public List<SurveyResult> getSurveyResultByResponding(@PathVariable String id){
    	log.debug("REST request to get SurveyResult by responding : {}", id);
        return surveyResultService.findByRespondingId(id); 
    }
    
    @GetMapping("/survey-results/findBySurveyAndResponding/{surveyId}/{respondingId}")
    @Timed
    public List<SurveyResult> findBySurveyAndResponding(@PathVariable String surveyId, @PathVariable String respondingId){
    	log.debug("REST request to get SurveyResult by responding : {}", surveyId);
        return surveyResultService.findBySurveyAndResponding(surveyId, respondingId); 
    }
    
    @PostMapping("/survey-results/publish")
    @Timed
    public List<SurveyResult> publish(@Valid @RequestBody RespondingSurveyInput respondingSurveyInput) throws URISyntaxException {
        log.debug("REST request to save respondingSurveyInput : {}", respondingSurveyInput);
        if (respondingSurveyInput.getResponding().getId() == null) {
            throw new BadRequestAlertException("A new responding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<SurveyResult> results = surveyResultService.publish(respondingSurveyInput);
        return results;
       
    }
    
    public TreeTableData getAllSurveyData() {
    	TreeTableData tableData = new TreeTableData();
    	surveyResultService.getAllSummrySurvryData();
    	return tableData;
    }
    
    
    public ChartDataWrapper getChartData(@RequestBody SurveyResult surveyResults[]) {
    	ChartDataWrapper chartDataWrapper = new ChartDataWrapper();
    	
    	
    	return chartDataWrapper;
    }
}
