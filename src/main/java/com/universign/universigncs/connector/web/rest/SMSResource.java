package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.domain.SMS;
import com.universign.universigncs.connector.repository.SMSRepository;
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
 * REST controller for managing {@link com.universign.universigncs.connector.domain.SMS}.
 */
@RestController
@RequestMapping("/api")
public class SMSResource {

    private final Logger log = LoggerFactory.getLogger(SMSResource.class);

    private static final String ENTITY_NAME = "sMS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SMSRepository sMSRepository;

    public SMSResource(SMSRepository sMSRepository) {
        this.sMSRepository = sMSRepository;
    }

    /**
     * {@code POST  /sms} : Create a new sMS.
     *
     * @param sMS the sMS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sMS, or with status {@code 400 (Bad Request)} if the sMS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sms")
    public ResponseEntity<SMS> createSMS(@RequestBody SMS sMS) throws URISyntaxException {
        log.debug("REST request to save SMS : {}", sMS);
        if (sMS.getId() != null) {
            throw new BadRequestAlertException("A new sMS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SMS result = sMSRepository.save(sMS);
        return ResponseEntity.created(new URI("/api/sms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sms} : Updates an existing sMS.
     *
     * @param sMS the sMS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sMS,
     * or with status {@code 400 (Bad Request)} if the sMS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sMS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sms")
    public ResponseEntity<SMS> updateSMS(@RequestBody SMS sMS) throws URISyntaxException {
        log.debug("REST request to update SMS : {}", sMS);
        if (sMS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SMS result = sMSRepository.save(sMS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sMS.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sms} : get all the sMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sMS in body.
     */
    @GetMapping("/sms")
    public List<SMS> getAllSMS() {
        log.debug("REST request to get all SMS");
        return sMSRepository.findAll();
    }

    /**
     * {@code GET  /sms/:id} : get the "id" sMS.
     *
     * @param id the id of the sMS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sMS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sms/{id}")
    public ResponseEntity<SMS> getSMS(@PathVariable String id) {
        log.debug("REST request to get SMS : {}", id);
        Optional<SMS> sMS = sMSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sMS);
    }

    /**
     * {@code DELETE  /sms/:id} : delete the "id" sMS.
     *
     * @param id the id of the sMS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sms/{id}")
    public ResponseEntity<Void> deleteSMS(@PathVariable String id) {
        log.debug("REST request to delete SMS : {}", id);
        sMSRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
