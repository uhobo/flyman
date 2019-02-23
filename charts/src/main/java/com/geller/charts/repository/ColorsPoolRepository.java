package com.geller.charts.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.geller.charts.domain.ColorPoolElm;

@Repository
public interface ColorsPoolRepository  extends MongoRepository<ColorPoolElm, String>{
	 
	 @Query("{}")
	 List<ColorPoolElm> findAllWithEagerRelationships();

}
