package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.domain.Emails;
import com.universign.universigncs.connector.repository.EmailsRepository;
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
 * REST controller for managing {@link com.universign.universigncs.connector.domain.Emails}.
 */
@RestController
@RequestMapping("/api")
public class EmailsResource {

    private final Logger log = LoggerFactory.getLogger(EmailsResource.class);

    private static final String ENTITY_NAME = "emails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailsRepository emailsRepository;

    public EmailsResource(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    /**
     * {@code POST  /emails} : Create a new emails.
     *
     * @param emails the emails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emails, or with status {@code 400 (Bad Request)} if the emails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emails")
    public ResponseEntity<Emails> createEmails(@RequestBody Emails emails) throws URISyntaxException {
        log.debug("REST request to save Emails : {}", emails);
        if (emails.getId() != null) {
            throw new BadRequestAlertException("A new emails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emails result = emailsRepository.save(emails);
        return ResponseEntity.created(new URI("/api/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emails} : Updates an existing emails.
     *
     * @param emails the emails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emails,
     * or with status {@code 400 (Bad Request)} if the emails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emails")
    public ResponseEntity<Emails> updateEmails(@RequestBody Emails emails) throws URISyntaxException {
        log.debug("REST request to update Emails : {}", emails);
        if (emails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Emails result = emailsRepository.save(emails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emails} : get all the emails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emails in body.
     */
    @GetMapping("/emails")
    public List<Emails> getAllEmails() {
        log.debug("REST request to get all Emails");
        return emailsRepository.findAll();
    }

    /**
     * {@code GET  /emails/:id} : get the "id" emails.
     *
     * @param id the id of the emails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emails/{id}")
    public ResponseEntity<Emails> getEmails(@PathVariable String id) {
        log.debug("REST request to get Emails : {}", id);
        Optional<Emails> emails = emailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emails);
    }

    /**
     * {@code DELETE  /emails/:id} : delete the "id" emails.
     *
     * @param id the id of the emails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emails/{id}")
    public ResponseEntity<Void> deleteEmails(@PathVariable String id) {
        log.debug("REST request to delete Emails : {}", id);
        emailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
