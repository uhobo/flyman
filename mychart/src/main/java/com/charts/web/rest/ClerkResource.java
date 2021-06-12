package com.charts.web.rest;

import com.charts.domain.Clerk;
import com.charts.repository.ClerkRepository;
import com.charts.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.charts.domain.Clerk}.
 */
@RestController
@RequestMapping("/api")
public class ClerkResource {

    private final Logger log = LoggerFactory.getLogger(ClerkResource.class);

    private static final String ENTITY_NAME = "clerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClerkRepository clerkRepository;

    public ClerkResource(ClerkRepository clerkRepository) {
        this.clerkRepository = clerkRepository;
    }

    /**
     * {@code POST  /clerks} : Create a new clerk.
     *
     * @param clerk the clerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clerk, or with status {@code 400 (Bad Request)} if the clerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clerks")
    public ResponseEntity<Clerk> createClerk(@Valid @RequestBody Clerk clerk) throws URISyntaxException {
        log.debug("REST request to save Clerk : {}", clerk);
        if (clerk.getId() != null) {
            throw new BadRequestAlertException("A new clerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clerk result = clerkRepository.save(clerk);
        return ResponseEntity.created(new URI("/api/clerks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clerks} : Updates an existing clerk.
     *
     * @param clerk the clerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clerk,
     * or with status {@code 400 (Bad Request)} if the clerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clerks")
    public ResponseEntity<Clerk> updateClerk(@Valid @RequestBody Clerk clerk) throws URISyntaxException {
        log.debug("REST request to update Clerk : {}", clerk);
        if (clerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clerk result = clerkRepository.save(clerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clerk.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clerks} : get all the clerks.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clerks in body.
     */
    @GetMapping("/clerks")
    public List<Clerk> getAllClerks() {
        log.debug("REST request to get all Clerks");
        return clerkRepository.findAll();
    }

    /**
     * {@code GET  /clerks/:id} : get the "id" clerk.
     *
     * @param id the id of the clerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clerks/{id}")
    public ResponseEntity<Clerk> getClerk(@PathVariable String id) {
        log.debug("REST request to get Clerk : {}", id);
        Optional<Clerk> clerk = clerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clerk);
    }

    /**
     * {@code DELETE  /clerks/:id} : delete the "id" clerk.
     *
     * @param id the id of the clerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clerks/{id}")
    public ResponseEntity<Void> deleteClerk(@PathVariable String id) {
        log.debug("REST request to delete Clerk : {}", id);
        clerkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
