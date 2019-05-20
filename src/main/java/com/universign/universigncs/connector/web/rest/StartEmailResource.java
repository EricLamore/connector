package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.domain.StartEmail;
import com.universign.universigncs.connector.repository.StartEmailRepository;
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
 * REST controller for managing {@link com.universign.universigncs.connector.domain.StartEmail}.
 */
@RestController
@RequestMapping("/api")
public class StartEmailResource {

    private final Logger log = LoggerFactory.getLogger(StartEmailResource.class);

    private static final String ENTITY_NAME = "startEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StartEmailRepository startEmailRepository;

    public StartEmailResource(StartEmailRepository startEmailRepository) {
        this.startEmailRepository = startEmailRepository;
    }

    /**
     * {@code POST  /start-emails} : Create a new startEmail.
     *
     * @param startEmail the startEmail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new startEmail, or with status {@code 400 (Bad Request)} if the startEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/start-emails")
    public ResponseEntity<StartEmail> createStartEmail(@RequestBody StartEmail startEmail) throws URISyntaxException {
        log.debug("REST request to save StartEmail : {}", startEmail);
        if (startEmail.getId() != null) {
            throw new BadRequestAlertException("A new startEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StartEmail result = startEmailRepository.save(startEmail);
        return ResponseEntity.created(new URI("/api/start-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /start-emails} : Updates an existing startEmail.
     *
     * @param startEmail the startEmail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startEmail,
     * or with status {@code 400 (Bad Request)} if the startEmail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the startEmail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/start-emails")
    public ResponseEntity<StartEmail> updateStartEmail(@RequestBody StartEmail startEmail) throws URISyntaxException {
        log.debug("REST request to update StartEmail : {}", startEmail);
        if (startEmail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StartEmail result = startEmailRepository.save(startEmail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, startEmail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /start-emails} : get all the startEmails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of startEmails in body.
     */
    @GetMapping("/start-emails")
    public List<StartEmail> getAllStartEmails() {
        log.debug("REST request to get all StartEmails");
        return startEmailRepository.findAll();
    }

    /**
     * {@code GET  /start-emails/:id} : get the "id" startEmail.
     *
     * @param id the id of the startEmail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the startEmail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/start-emails/{id}")
    public ResponseEntity<StartEmail> getStartEmail(@PathVariable String id) {
        log.debug("REST request to get StartEmail : {}", id);
        Optional<StartEmail> startEmail = startEmailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(startEmail);
    }

    /**
     * {@code DELETE  /start-emails/:id} : delete the "id" startEmail.
     *
     * @param id the id of the startEmail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/start-emails/{id}")
    public ResponseEntity<Void> deleteStartEmail(@PathVariable String id) {
        log.debug("REST request to delete StartEmail : {}", id);
        startEmailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
