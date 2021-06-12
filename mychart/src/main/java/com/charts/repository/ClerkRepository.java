package com.charts.repository;

import com.charts.domain.Clerk;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Clerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClerkRepository extends MongoRepository<Clerk, String> {

}
