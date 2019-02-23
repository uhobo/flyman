package com.geller.charts.service.impl;

import com.geller.charts.service.PersonService;
import com.geller.charts.domain.Person;
import com.geller.charts.domain.PersonType;
import com.geller.charts.repository.PersonRepository;
import com.geller.charts.repository.PersonTypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Person.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private PersonRepository personRepository;
    
    private PersonTypeRepository personTypeRepository;

    public PersonServiceImpl(PersonRepository personRepository, PersonTypeRepository personTypeRepository) {
        this.personRepository = personRepository;
        this.personTypeRepository = personTypeRepository;
    }

    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    @Override
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    /**
     * Get all the people.
     *
     * @return the list of entities
     */
    @Override
    public List<Person> findAll() {
        log.debug("Request to get all People");
        return personRepository.findAll();
    }
    
    
    


    /**
     * Get one person by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Person> findOne(String id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findById(id);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }

	@Override
	public List<PersonType> findAllPersonTypes() {
		log.debug("Request to get all person Types");
		return personTypeRepository.findAll();
	}

	@Override
	public Optional<PersonType> findOnePersonType(String id) {
		log.debug("Request to get Person Type: {}", id);
		return personTypeRepository.findById(id);
	}

	@Override
	public PersonType savePersonType(PersonType personType) {
		log.debug("Request to save Person Type : {}", personType);
		return personTypeRepository.save(personType);
	}
}
