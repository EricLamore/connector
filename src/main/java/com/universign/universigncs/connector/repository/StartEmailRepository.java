package com.universign.universigncs.connector.repository;

import com.universign.universigncs.connector.domain.StartEmail;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the StartEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StartEmailRepository extends MongoRepository<StartEmail, String> {

}
