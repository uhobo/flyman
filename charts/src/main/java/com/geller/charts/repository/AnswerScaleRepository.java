package com.geller.charts.repository;

import com.geller.charts.domain.AnswerScale;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AnswerScale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerScaleRepository extends MongoRepository<AnswerScale, String> {

}
