package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.RelaunchEmail;
import com.universign.universigncs.connector.repository.RelaunchEmailRepository;
import com.universign.universigncs.connector.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.universign.universigncs.connector.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RelaunchEmailResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class RelaunchEmailResourceIT {

    private static final String DEFAULT_RELAUNCH_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_RELAUNCH_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_RELAUNCH_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RELAUNCH_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RELAUNCH_END_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RELAUNCH_END_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RELAUNCH_SIGNATURE_LINK_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RELAUNCH_SIGNATURE_LINK_BODY = "BBBBBBBBBB";

    @Autowired
    private RelaunchEmailRepository relaunchEmailRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restRelaunchEmailMockMvc;

    private RelaunchEmail relaunchEmail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelaunchEmailResource relaunchEmailResource = new RelaunchEmailResource(relaunchEmailRepository);
        this.restRelaunchEmailMockMvc = MockMvcBuilders.standaloneSetup(relaunchEmailResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelaunchEmail createEntity() {
        RelaunchEmail relaunchEmail = new RelaunchEmail()
            .relaunchSubject(DEFAULT_RELAUNCH_SUBJECT)
            .relaunchBody(DEFAULT_RELAUNCH_BODY)
            .relaunchEndBody(DEFAULT_RELAUNCH_END_BODY)
            .relaunchSignatureLinkBody(DEFAULT_RELAUNCH_SIGNATURE_LINK_BODY);
        return relaunchEmail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelaunchEmail createUpdatedEntity() {
        RelaunchEmail relaunchEmail = new RelaunchEmail()
            .relaunchSubject(UPDATED_RELAUNCH_SUBJECT)
            .relaunchBody(UPDATED_RELAUNCH_BODY)
            .relaunchEndBody(UPDATED_RELAUNCH_END_BODY)
            .relaunchSignatureLinkBody(UPDATED_RELAUNCH_SIGNATURE_LINK_BODY);
        return relaunchEmail;
    }

    @BeforeEach
    public void initTest() {
        relaunchEmailRepository.deleteAll();
        relaunchEmail = createEntity();
    }

    @Test
    public void createRelaunchEmail() throws Exception {
        int databaseSizeBeforeCreate = relaunchEmailRepository.findAll().size();

        // Create the RelaunchEmail
        restRelaunchEmailMockMvc.perform(post("/api/relaunch-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relaunchEmail)))
            .andExpect(status().isCreated());

        // Validate the RelaunchEmail in the database
        List<RelaunchEmail> relaunchEmailList = relaunchEmailRepository.findAll();
        assertThat(relaunchEmailList).hasSize(databaseSizeBeforeCreate + 1);
        RelaunchEmail testRelaunchEmail = relaunchEmailList.get(relaunchEmailList.size() - 1);
        assertThat(testRelaunchEmail.getRelaunchSubject()).isEqualTo(DEFAULT_RELAUNCH_SUBJECT);
        assertThat(testRelaunchEmail.getRelaunchBody()).isEqualTo(DEFAULT_RELAUNCH_BODY);
        assertThat(testRelaunchEmail.getRelaunchEndBody()).isEqualTo(DEFAULT_RELAUNCH_END_BODY);
        assertThat(testRelaunchEmail.getRelaunchSignatureLinkBody()).isEqualTo(DEFAULT_RELAUNCH_SIGNATURE_LINK_BODY);
    }

    @Test
    public void createRelaunchEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relaunchEmailRepository.findAll().size();

        // Create the RelaunchEmail with an existing ID
        relaunchEmail.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelaunchEmailMockMvc.perform(post("/api/relaunch-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relaunchEmail)))
            .andExpect(status().isBadRequest());

        // Validate the RelaunchEmail in the database
        List<RelaunchEmail> relaunchEmailList = relaunchEmailRepository.findAll();
        assertThat(relaunchEmailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllRelaunchEmails() throws Exception {
        // Initialize the database
        relaunchEmailRepository.save(relaunchEmail);

        // Get all the relaunchEmailList
        restRelaunchEmailMockMvc.perform(get("/api/relaunch-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relaunchEmail.getId())))
            .andExpect(jsonPath("$.[*].relaunchSubject").value(hasItem(DEFAULT_RELAUNCH_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].relaunchBody").value(hasItem(DEFAULT_RELAUNCH_BODY.toString())))
            .andExpect(jsonPath("$.[*].relaunchEndBody").value(hasItem(DEFAULT_RELAUNCH_END_BODY.toString())))
            .andExpect(jsonPath("$.[*].relaunchSignatureLinkBody").value(hasItem(DEFAULT_RELAUNCH_SIGNATURE_LINK_BODY.toString())));
    }
    
    @Test
    public void getRelaunchEmail() throws Exception {
        // Initialize the database
        relaunchEmailRepository.save(relaunchEmail);

        // Get the relaunchEmail
        restRelaunchEmailMockMvc.perform(get("/api/relaunch-emails/{id}", relaunchEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relaunchEmail.getId()))
            .andExpect(jsonPath("$.relaunchSubject").value(DEFAULT_RELAUNCH_SUBJECT.toString()))
            .andExpect(jsonPath("$.relaunchBody").value(DEFAULT_RELAUNCH_BODY.toString()))
            .andExpect(jsonPath("$.relaunchEndBody").value(DEFAULT_RELAUNCH_END_BODY.toString()))
            .andExpect(jsonPath("$.relaunchSignatureLinkBody").value(DEFAULT_RELAUNCH_SIGNATURE_LINK_BODY.toString()));
    }

    @Test
    public void getNonExistingRelaunchEmail() throws Exception {
        // Get the relaunchEmail
        restRelaunchEmailMockMvc.perform(get("/api/relaunch-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRelaunchEmail() throws Exception {
        // Initialize the database
        relaunchEmailRepository.save(relaunchEmail);

        int databaseSizeBeforeUpdate = relaunchEmailRepository.findAll().size();

        // Update the relaunchEmail
        RelaunchEmail updatedRelaunchEmail = relaunchEmailRepository.findById(relaunchEmail.getId()).get();
        updatedRelaunchEmail
            .relaunchSubject(UPDATED_RELAUNCH_SUBJECT)
            .relaunchBody(UPDATED_RELAUNCH_BODY)
            .relaunchEndBody(UPDATED_RELAUNCH_END_BODY)
            .relaunchSignatureLinkBody(UPDATED_RELAUNCH_SIGNATURE_LINK_BODY);

        restRelaunchEmailMockMvc.perform(put("/api/relaunch-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelaunchEmail)))
            .andExpect(status().isOk());

        // Validate the RelaunchEmail in the database
        List<RelaunchEmail> relaunchEmailList = relaunchEmailRepository.findAll();
        assertThat(relaunchEmailList).hasSize(databaseSizeBeforeUpdate);
        RelaunchEmail testRelaunchEmail = relaunchEmailList.get(relaunchEmailList.size() - 1);
        assertThat(testRelaunchEmail.getRelaunchSubject()).isEqualTo(UPDATED_RELAUNCH_SUBJECT);
        assertThat(testRelaunchEmail.getRelaunchBody()).isEqualTo(UPDATED_RELAUNCH_BODY);
        assertThat(testRelaunchEmail.getRelaunchEndBody()).isEqualTo(UPDATED_RELAUNCH_END_BODY);
        assertThat(testRelaunchEmail.getRelaunchSignatureLinkBody()).isEqualTo(UPDATED_RELAUNCH_SIGNATURE_LINK_BODY);
    }

    @Test
    public void updateNonExistingRelaunchEmail() throws Exception {
        int databaseSizeBeforeUpdate = relaunchEmailRepository.findAll().size();

        // Create the RelaunchEmail

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelaunchEmailMockMvc.perform(put("/api/relaunch-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relaunchEmail)))
            .andExpect(status().isBadRequest());

        // Validate the RelaunchEmail in the database
        List<RelaunchEmail> relaunchEmailList = relaunchEmailRepository.findAll();
        assertThat(relaunchEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRelaunchEmail() throws Exception {
        // Initialize the database
        relaunchEmailRepository.save(relaunchEmail);

        int databaseSizeBeforeDelete = relaunchEmailRepository.findAll().size();

        // Delete the relaunchEmail
        restRelaunchEmailMockMvc.perform(delete("/api/relaunch-emails/{id}", relaunchEmail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RelaunchEmail> relaunchEmailList = relaunchEmailRepository.findAll();
        assertThat(relaunchEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelaunchEmail.class);
        RelaunchEmail relaunchEmail1 = new RelaunchEmail();
        relaunchEmail1.setId("id1");
        RelaunchEmail relaunchEmail2 = new RelaunchEmail();
        relaunchEmail2.setId(relaunchEmail1.getId());
        assertThat(relaunchEmail1).isEqualTo(relaunchEmail2);
        relaunchEmail2.setId("id2");
        assertThat(relaunchEmail1).isNotEqualTo(relaunchEmail2);
        relaunchEmail1.setId(null);
        assertThat(relaunchEmail1).isNotEqualTo(relaunchEmail2);
    }
}
