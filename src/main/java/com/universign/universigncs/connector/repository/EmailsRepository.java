package com.universign.universigncs.connector.repository;

import com.universign.universigncs.connector.domain.Emails;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Emails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailsRepository extends MongoRepository<Emails, String> {

}
