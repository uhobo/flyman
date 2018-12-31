package com.geller.charts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geller.charts.domain.Asked;
import com.geller.charts.service.AskedService;
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
 * REST controller for managing Asked.
 */
@RestController
@RequestMapping("/api")
public class AskedResource {

    private final Logger log = LoggerFactory.getLogger(AskedResource.class);

    private static final String ENTITY_NAME = "asked";

    private AskedService askedService;

    public AskedResource(AskedService askedService) {
        this.askedService = askedService;
    }

    /**
     * POST  /askeds : Create a new asked.
     *
     * @param asked the asked to create
     * @return the ResponseEntity with status 201 (Created) and with body the new asked, or with status 400 (Bad Request) if the asked has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/askeds")
    @Timed
    public ResponseEntity<Asked> createAsked(@Valid @RequestBody Asked asked) throws URISyntaxException {
        log.debug("REST request to save Asked : {}", asked);
        if (asked.getId() != null) {
            throw new BadRequestAlertException("A new asked cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Asked result = askedService.save(asked);
        return ResponseEntity.created(new URI("/api/askeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /askeds : Updates an existing asked.
     *
     * @param asked the asked to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asked,
     * or with status 400 (Bad Request) if the asked is not valid,
     * or with status 500 (Internal Server Error) if the asked couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/askeds")
    @Timed
    public ResponseEntity<Asked> updateAsked(@Valid @RequestBody Asked asked) throws URISyntaxException {
        log.debug("REST request to update Asked : {}", asked);
        if (asked.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Asked result = askedService.save(asked);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asked.getId().toString()))
            .body(result);
    }

    /**
     * GET  /askeds : get all the askeds.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of askeds in body
     */
    @GetMapping("/askeds")
    @Timed
    public List<Asked> getAllAskeds(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Askeds");
        return askedService.findAll();
    }

    /**
     * GET  /askeds/:id : get the "id" asked.
     *
     * @param id the id of the asked to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the asked, or with status 404 (Not Found)
     */
    @GetMapping("/askeds/{id}")
    @Timed
    public ResponseEntity<Asked> getAsked(@PathVariable String id) {
        log.debug("REST request to get Asked : {}", id);
        Optional<Asked> asked = askedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asked);
    }

    /**
     * DELETE  /askeds/:id : delete the "id" asked.
     *
     * @param id the id of the asked to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/askeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteAsked(@PathVariable String id) {
        log.debug("REST request to delete Asked : {}", id);
        askedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
