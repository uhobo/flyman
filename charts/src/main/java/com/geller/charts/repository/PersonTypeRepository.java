package com.geller.charts.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.geller.charts.domain.PersonType;

public interface PersonTypeRepository extends MongoRepository<PersonType, String> {

}
