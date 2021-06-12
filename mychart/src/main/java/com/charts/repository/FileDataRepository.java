package com.charts.repository;

import com.charts.domain.FileData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the FileData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileDataRepository extends MongoRepository<FileData, String> {

}
