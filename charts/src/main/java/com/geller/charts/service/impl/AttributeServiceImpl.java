package com.geller.charts.service.impl;

import com.geller.charts.service.AttributeService;
import com.geller.charts.domain.Attribute;
import com.geller.charts.domain.AttributeType;
import com.geller.charts.domain.AttributeValue;
import com.geller.charts.repository.AttributeRepository;
import com.geller.charts.repository.AttributeTypeRepository;
import com.geller.charts.repository.AttributeValueRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Attribute.
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    private final Logger log = LoggerFactory.getLogger(AttributeServiceImpl.class);

    private AttributeRepository attributeRepository;
    
    private AttributeTypeRepository attributeTypeRepository;
    
    private AttributeValueRepository attributeValueRepository;
    

    public AttributeServiceImpl(AttributeRepository attributeRepository, AttributeTypeRepository attributeTypeRepository, AttributeValueRepository attributeValueRepository) {
        this.attributeRepository = attributeRepository;
        this.attributeTypeRepository = attributeTypeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    /**
     * Save a attribute.
     *
     * @param attribute the entity to save
     * @return the persisted entity
     */
    @Override
    public Attribute save(Attribute attribute) {
        log.debug("Request to save Attribute : {}", attribute);
        
        for(AttributeValue attributeValue: attribute.getValuesList()) {
        	if(attributeValue.getId() == null) {
        		log.debug("Create new Attribute Value: {}", attributeValue);
        		attributeValue = this.attributeValueRepository.save(attributeValue);
        	}
        }
        
        return attributeRepository.save(attribute);
    }

    /**
     * Get all the attributes.
     *
     * @return the list of entities
     */
    @Override
    public List<Attribute> findAll() {
        log.debug("Request to get all Attributes");
        return attributeRepository.findAll();
    }

    
    public List<AttributeType> getAllAttributeTypes(){
    	log.debug("Request to get all Attribute Types");
    	return attributeTypeRepository.findAll();
    }
    /**
     * Get one attribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Attribute> findOne(String id) {
        log.debug("Request to get Attribute : {}", id);
        return attributeRepository.findById(id);
    }

    /**
     * Delete the attribute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Attribute : {}", id);
        Optional<Attribute> attribute = this.findOne(id);
        if(attribute.isPresent()) {
        	for(AttributeValue attributeValue: attribute.get().getValuesList()) {
            	log.debug("delete Attribute Value: {}", attributeValue);
            	this.attributeValueRepository.delete(attributeValue);
            }	
        }
        
        attributeRepository.deleteById(id);
    }
}
