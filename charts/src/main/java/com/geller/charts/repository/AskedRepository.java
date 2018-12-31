package com.geller.charts.repository;

import com.geller.charts.domain.Asked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Asked entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AskedRepository extends MongoRepository<Asked, String> {
    @Query("{}")
    Page<Asked> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Asked> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Asked> findOneWithEagerRelationships(String id);

}
