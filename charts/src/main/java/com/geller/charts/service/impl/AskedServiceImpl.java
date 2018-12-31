package com.geller.charts.service.impl;

import com.geller.charts.service.AskedService;
import com.geller.charts.domain.Asked;
import com.geller.charts.repository.AskedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Asked.
 */
@Service
public class AskedServiceImpl implements AskedService {

    private final Logger log = LoggerFactory.getLogger(AskedServiceImpl.class);

    private AskedRepository askedRepository;

    public AskedServiceImpl(AskedRepository askedRepository) {
        this.askedRepository = askedRepository;
    }

    /**
     * Save a asked.
     *
     * @param asked the entity to save
     * @return the persisted entity
     */
    @Override
    public Asked save(Asked asked) {
        log.debug("Request to save Asked : {}", asked);
        return askedRepository.save(asked);
    }

    /**
     * Get all the askeds.
     *
     * @return the list of entities
     */
    @Override
    public List<Asked> findAll() {
        log.debug("Request to get all Askeds");
        return askedRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Asked with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Asked> findAllWithEagerRelationships(Pageable pageable) {
        return askedRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one asked by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Asked> findOne(String id) {
        log.debug("Request to get Asked : {}", id);
        return askedRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the asked by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Asked : {}", id);
        askedRepository.deleteById(id);
    }
}
