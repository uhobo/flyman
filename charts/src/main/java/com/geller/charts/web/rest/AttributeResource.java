package com.geller.charts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geller.charts.domain.Attribute;
import com.geller.charts.domain.AttributeType;
import com.geller.charts.service.AttributeService;
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
 * REST controller for managing Attribute.
 */
@RestController
@RequestMapping("/api")
public class AttributeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeResource.class);

    private static final String ENTITY_NAME = "attribute";

    private AttributeService attributeService;

    public AttributeResource(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    /**
     * POST  /attributes : Create a new attribute.
     *
     * @param attribute the attribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attribute, or with status 400 (Bad Request) if the attribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attributes")
    @Timed
    public ResponseEntity<Attribute> createAttribute(@Valid @RequestBody Attribute attribute) throws URISyntaxException {
        log.debug("REST request to save Attribute : {}", attribute);
        if (attribute.getId() != null) {
            throw new BadRequestAlertException("A new attribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attribute result = attributeService.save(attribute);
        return ResponseEntity.created(new URI("/api/attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attributes : Updates an existing attribute.
     *
     * @param attribute the attribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attribute,
     * or with status 400 (Bad Request) if the attribute is not valid,
     * or with status 500 (Internal Server Error) if the attribute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attributes")
    @Timed
    public ResponseEntity<Attribute> updateAttribute(@Valid @RequestBody Attribute attribute) throws URISyntaxException {
        log.debug("REST request to update Attribute : {}", attribute);
        if (attribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Attribute result = attributeService.save(attribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attributes : get all the attributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attributes in body
     */
    @GetMapping("/attributes")
    @Timed
    public List<Attribute> getAllAttributes() {
        log.debug("REST request to get all Attributes");
        return attributeService.findAll();
    }
    
    
    @GetMapping("/attributeTypes")
    @Timed
    public List<AttributeType> getAllAttributeTypes() {
        log.debug("REST request to get all Attribute Types");
        return attributeService.getAllAttributeTypes();
    }


    /**
     * GET  /attributes/:id : get the "id" attribute.
     *
     * @param id the id of the attribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attribute, or with status 404 (Not Found)
     */
    @GetMapping("/attributes/{id}")
    @Timed
    public ResponseEntity<Attribute> getAttribute(@PathVariable String id) {
        log.debug("REST request to get Attribute : {}", id);
        Optional<Attribute> attribute = attributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attribute);
    }

    /**
     * DELETE  /attributes/:id : delete the "id" attribute.
     *
     * @param id the id of the attribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttribute(@PathVariable String id) {
        log.debug("REST request to delete Attribute : {}", id);
        attributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
