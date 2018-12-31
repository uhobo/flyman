package com.geller.charts.repository;

import com.geller.charts.domain.Answer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {

}
