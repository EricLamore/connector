package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.domain.RelaunchEmail;
import com.universign.universigncs.connector.repository.RelaunchEmailRepository;
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
 * REST controller for managing {@link com.universign.universigncs.connector.domain.RelaunchEmail}.
 */
@RestController
@RequestMapping("/api")
public class RelaunchEmailResource {

    private final Logger log = LoggerFactory.getLogger(RelaunchEmailResource.class);

    private static final String ENTITY_NAME = "relaunchEmail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelaunchEmailRepository relaunchEmailRepository;

    public RelaunchEmailResource(RelaunchEmailRepository relaunchEmailRepository) {
        this.relaunchEmailRepository = relaunchEmailRepository;
    }

    /**
     * {@code POST  /relaunch-emails} : Create a new relaunchEmail.
     *
     * @param relaunchEmail the relaunchEmail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relaunchEmail, or with status {@code 400 (Bad Request)} if the relaunchEmail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relaunch-emails")
    public ResponseEntity<RelaunchEmail> createRelaunchEmail(@RequestBody RelaunchEmail relaunchEmail) throws URISyntaxException {
        log.debug("REST request to save RelaunchEmail : {}", relaunchEmail);
        if (relaunchEmail.getId() != null) {
            throw new BadRequestAlertException("A new relaunchEmail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelaunchEmail result = relaunchEmailRepository.save(relaunchEmail);
        return ResponseEntity.created(new URI("/api/relaunch-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relaunch-emails} : Updates an existing relaunchEmail.
     *
     * @param relaunchEmail the relaunchEmail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relaunchEmail,
     * or with status {@code 400 (Bad Request)} if the relaunchEmail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relaunchEmail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relaunch-emails")
    public ResponseEntity<RelaunchEmail> updateRelaunchEmail(@RequestBody RelaunchEmail relaunchEmail) throws URISyntaxException {
        log.debug("REST request to update RelaunchEmail : {}", relaunchEmail);
        if (relaunchEmail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RelaunchEmail result = relaunchEmailRepository.save(relaunchEmail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relaunchEmail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relaunch-emails} : get all the relaunchEmails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relaunchEmails in body.
     */
    @GetMapping("/relaunch-emails")
    public List<RelaunchEmail> getAllRelaunchEmails() {
        log.debug("REST request to get all RelaunchEmails");
        return relaunchEmailRepository.findAll();
    }

    /**
     * {@code GET  /relaunch-emails/:id} : get the "id" relaunchEmail.
     *
     * @param id the id of the relaunchEmail to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relaunchEmail, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relaunch-emails/{id}")
    public ResponseEntity<RelaunchEmail> getRelaunchEmail(@PathVariable String id) {
        log.debug("REST request to get RelaunchEmail : {}", id);
        Optional<RelaunchEmail> relaunchEmail = relaunchEmailRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(relaunchEmail);
    }

    /**
     * {@code DELETE  /relaunch-emails/:id} : delete the "id" relaunchEmail.
     *
     * @param id the id of the relaunchEmail to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relaunch-emails/{id}")
    public ResponseEntity<Void> deleteRelaunchEmail(@PathVariable String id) {
        log.debug("REST request to delete RelaunchEmail : {}", id);
        relaunchEmailRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
