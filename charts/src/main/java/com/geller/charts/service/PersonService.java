package com.geller.charts.service;

import com.geller.charts.domain.Person;
import com.geller.charts.domain.PersonType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Person.
 */
public interface PersonService {

    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    Person save(Person person);

    /**
     * Get all the people.
     *
     * @return the list of entities
     */
    List<Person> findAll();


    /**
     * Get the "id" person.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Person> findOne(String id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity
     */
    void delete(String id);
    
    
    List<PersonType> findAllPersonTypes();
    
    Optional<PersonType> findOnePersonType(String id);
    
    PersonType savePersonType(PersonType personType);
    
    
}
