package com.universign.universigncs.connector.repository;

import com.universign.universigncs.connector.domain.RelaunchEmail;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the RelaunchEmail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelaunchEmailRepository extends MongoRepository<RelaunchEmail, String> {

}
