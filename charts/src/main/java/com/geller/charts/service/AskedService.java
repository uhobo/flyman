package com.geller.charts.service;

import com.geller.charts.domain.Asked;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Asked.
 */
public interface AskedService {

    /**
     * Save a asked.
     *
     * @param asked the entity to save
     * @return the persisted entity
     */
    Asked save(Asked asked);

    /**
     * Get all the askeds.
     *
     * @return the list of entities
     */
    List<Asked> findAll();

    /**
     * Get all the Asked with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Asked> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" asked.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Asked> findOne(String id);

    /**
     * Delete the "id" asked.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
