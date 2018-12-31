package com.geller.charts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geller.charts.domain.Responding;
import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.SurveyResult;
import com.geller.charts.service.RespondingService;
import com.geller.charts.web.rest.errors.BadRequestAlertException;
import com.geller.charts.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Responding.
 */
@RestController
@RequestMapping("/api")
public class RespondingResource {

    private final Logger log = LoggerFactory.getLogger(RespondingResource.class);

    private static final String ENTITY_NAME = "responding";

    private RespondingService respondingService;

    public RespondingResource(RespondingService respondingService) {
        this.respondingService = respondingService;
    }

    /**
     * POST  /respondings : Create a new responding.
     *
     * @param responding the responding to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responding, or with status 400 (Bad Request) if the responding has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/respondings")
    @Timed
    public ResponseEntity<Responding> createResponding(@Valid @RequestBody Responding responding) throws URISyntaxException {
        log.debug("REST request to save Responding : {}", responding);
        if (responding.getId() != null) {
            throw new BadRequestAlertException("A new responding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Responding result = respondingService.save(responding);
        return ResponseEntity.created(new URI("/api/respondings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    
   


    /**
     * PUT  /respondings : Updates an existing responding.
     *
     * @param responding the responding to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responding,
     * or with status 400 (Bad Request) if the responding is not valid,
     * or with status 500 (Internal Server Error) if the responding couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/respondings")
    @Timed
    public ResponseEntity<Responding> updateResponding(@Valid @RequestBody Responding responding) throws URISyntaxException {
        log.debug("REST request to update Responding : {}", responding);
        if (responding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Responding result = respondingService.save(responding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /respondings : get all the respondings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of respondings in body
     */
    @GetMapping("/respondings")
    @Timed
    public List<Responding> getAllRespondings(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Respondings");
        return respondingService.findAll();
    }

    /**
     * GET  /respondings/:id : get the "id" responding.
     *
     * @param id the id of the responding to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responding, or with status 404 (Not Found)
     */
    @GetMapping("/respondings/{id}")
    @Timed
    public ResponseEntity<Responding> getResponding(@PathVariable String id) {
        log.debug("REST request to get Responding : {}", id);
        Optional<Responding> responding = respondingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responding);
    }

    /**
     * DELETE  /respondings/:id : delete the "id" responding.
     *
     * @param id the id of the responding to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/respondings/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponding(@PathVariable String id) {
        log.debug("REST request to delete Responding : {}", id);
        respondingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    
    
    
    
    
    
   
}
