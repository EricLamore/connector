package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.domain.EndEmail;
import com.universign.universigncs.connector.repository.EndEmailRepository;
import com.universign.universigncs.connector.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.universign.universigncs.connector.domain.EndEmail}.
 */
@RestController
@RequestMapping("/api")
public class EndEmailResource {

    private final Logger log = LoggerFactory.getLogger(EndEmailResource.class);

    private static final String ENTITY_NAME = "endEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndEmailRepository endEmailRepository;

    public EndEmailResource(EndEmailRepository endEmailRepository) {
        this.endEmailRepository = endEmailRepository;
    }

    /**
     * {@code POST  /end-emails} : Create a new endEmail.
     *
     * @param endEmail the endEmail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endEmail, or with status {@code 400 (Bad Request)} if the endEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/end-emails")
    public ResponseEntity<EndEmail> createEndEmail(@RequestBody EndEmail endEmail) throws URISyntaxException {
        log.debug("REST request to save EndEmail : {}", endEmail);
        if (endEmail.getId() != null) {
            throw new BadRequestAlertException("A new endEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndEmail result = endEmailRepository.save(endEmail);
        return ResponseEntity.created(new URI("/api/end-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /end-emails} : Updates an existing endEmail.
     *
     * @param endEmail the endEmail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endEmail,
     * or with status {@code 400 (Bad Request)} if the endEmail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endEmail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/end-emails")
    public ResponseEntity<EndEmail> updateEndEmail(@RequestBody EndEmail endEmail) throws URISyntaxException {
        log.debug("REST request to update EndEmail : {}", endEmail);
        if (endEmail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EndEmail result = endEmailRepository.save(endEmail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endEmail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /end-emails} : get all the endEmails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endEmails in body.
     */
    @GetMapping("/end-emails")
    public List<EndEmail> getAllEndEmails() {
        log.debug("REST request to get all EndEmails");
        return endEmailRepository.findAll();
    }

    /**
     * {@code GET  /end-emails/:id} : get the "id" endEmail.
     *
     * @param id the id of the endEmail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endEmail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/end-emails/{id}")
    public ResponseEntity<EndEmail> getEndEmail(@PathVariable String id) {
        log.debug("REST request to get EndEmail : {}", id);
        Optional<EndEmail> endEmail = endEmailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(endEmail);
    }

    /**
     * {@code DELETE  /end-emails/:id} : delete the "id" endEmail.
     *
     * @param id the id of the endEmail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/end-emails/{id}")
    public ResponseEntity<Void> deleteEndEmail(@PathVariable String id) {
        log.debug("REST request to delete EndEmail : {}", id);
        endEmailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
