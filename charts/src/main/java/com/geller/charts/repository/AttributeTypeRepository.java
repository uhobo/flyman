package com.geller.charts.repository;

import com.geller.charts.domain.Attribute;
import com.geller.charts.domain.AttributeType;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Attribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeTypeRepository extends MongoRepository<AttributeType, String> {

}
