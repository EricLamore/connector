package com.universign.universigncs.connector.repository;

import com.universign.universigncs.connector.domain.EndEmail;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the EndEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndEmailRepository extends MongoRepository<EndEmail, String> {

}
