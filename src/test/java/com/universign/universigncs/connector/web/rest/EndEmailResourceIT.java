package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.EndEmail;
import com.universign.universigncs.connector.repository.EndEmailRepository;
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
 * Integration tests for the {@Link EndEmailResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class EndEmailResourceIT {

    private static final String DEFAULT_END_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_END_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_END_BODY = "AAAAAAAAAA";
    private static final String UPDATED_END_BODY = "BBBBBBBBBB";

    @Autowired
    private EndEmailRepository endEmailRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEndEmailMockMvc;

    private EndEmail endEmail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EndEmailResource endEmailResource = new EndEmailResource(endEmailRepository);
        this.restEndEmailMockMvc = MockMvcBuilders.standaloneSetup(endEmailResource)
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
    public static EndEmail createEntity() {
        EndEmail endEmail = new EndEmail()
            .endSubject(DEFAULT_END_SUBJECT)
            .endBody(DEFAULT_END_BODY);
        return endEmail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndEmail createUpdatedEntity() {
        EndEmail endEmail = new EndEmail()
            .endSubject(UPDATED_END_SUBJECT)
            .endBody(UPDATED_END_BODY);
        return endEmail;
    }

    @BeforeEach
    public void initTest() {
        endEmailRepository.deleteAll();
        endEmail = createEntity();
    }

    @Test
    public void createEndEmail() throws Exception {
        int databaseSizeBeforeCreate = endEmailRepository.findAll().size();

        // Create the EndEmail
        restEndEmailMockMvc.perform(post("/api/end-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endEmail)))
            .andExpect(status().isCreated());

        // Validate the EndEmail in the database
        List<EndEmail> endEmailList = endEmailRepository.findAll();
        assertThat(endEmailList).hasSize(databaseSizeBeforeCreate + 1);
        EndEmail testEndEmail = endEmailList.get(endEmailList.size() - 1);
        assertThat(testEndEmail.getEndSubject()).isEqualTo(DEFAULT_END_SUBJECT);
        assertThat(testEndEmail.getEndBody()).isEqualTo(DEFAULT_END_BODY);
    }

    @Test
    public void createEndEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endEmailRepository.findAll().size();

        // Create the EndEmail with an existing ID
        endEmail.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndEmailMockMvc.perform(post("/api/end-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endEmail)))
            .andExpect(status().isBadRequest());

        // Validate the EndEmail in the database
        List<EndEmail> endEmailList = endEmailRepository.findAll();
        assertThat(endEmailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEndEmails() throws Exception {
        // Initialize the database
        endEmailRepository.save(endEmail);

        // Get all the endEmailList
        restEndEmailMockMvc.perform(get("/api/end-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endEmail.getId())))
            .andExpect(jsonPath("$.[*].endSubject").value(hasItem(DEFAULT_END_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].endBody").value(hasItem(DEFAULT_END_BODY.toString())));
    }
    
    @Test
    public void getEndEmail() throws Exception {
        // Initialize the database
        endEmailRepository.save(endEmail);

        // Get the endEmail
        restEndEmailMockMvc.perform(get("/api/end-emails/{id}", endEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endEmail.getId()))
            .andExpect(jsonPath("$.endSubject").value(DEFAULT_END_SUBJECT.toString()))
            .andExpect(jsonPath("$.endBody").value(DEFAULT_END_BODY.toString()));
    }

    @Test
    public void getNonExistingEndEmail() throws Exception {
        // Get the endEmail
        restEndEmailMockMvc.perform(get("/api/end-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEndEmail() throws Exception {
        // Initialize the database
        endEmailRepository.save(endEmail);

        int databaseSizeBeforeUpdate = endEmailRepository.findAll().size();

        // Update the endEmail
        EndEmail updatedEndEmail = endEmailRepository.findById(endEmail.getId()).get();
        updatedEndEmail
            .endSubject(UPDATED_END_SUBJECT)
            .endBody(UPDATED_END_BODY);

        restEndEmailMockMvc.perform(put("/api/end-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndEmail)))
            .andExpect(status().isOk());

        // Validate the EndEmail in the database
        List<EndEmail> endEmailList = endEmailRepository.findAll();
        assertThat(endEmailList).hasSize(databaseSizeBeforeUpdate);
        EndEmail testEndEmail = endEmailList.get(endEmailList.size() - 1);
        assertThat(testEndEmail.getEndSubject()).isEqualTo(UPDATED_END_SUBJECT);
        assertThat(testEndEmail.getEndBody()).isEqualTo(UPDATED_END_BODY);
    }

    @Test
    public void updateNonExistingEndEmail() throws Exception {
        int databaseSizeBeforeUpdate = endEmailRepository.findAll().size();

        // Create the EndEmail

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndEmailMockMvc.perform(put("/api/end-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endEmail)))
            .andExpect(status().isBadRequest());

        // Validate the EndEmail in the database
        List<EndEmail> endEmailList = endEmailRepository.findAll();
        assertThat(endEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEndEmail() throws Exception {
        // Initialize the database
        endEmailRepository.save(endEmail);

        int databaseSizeBeforeDelete = endEmailRepository.findAll().size();

        // Delete the endEmail
        restEndEmailMockMvc.perform(delete("/api/end-emails/{id}", endEmail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EndEmail> endEmailList = endEmailRepository.findAll();
        assertThat(endEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndEmail.class);
        EndEmail endEmail1 = new EndEmail();
        endEmail1.setId("id1");
        EndEmail endEmail2 = new EndEmail();
        endEmail2.setId(endEmail1.getId());
        assertThat(endEmail1).isEqualTo(endEmail2);
        endEmail2.setId("id2");
        assertThat(endEmail1).isNotEqualTo(endEmail2);
        endEmail1.setId(null);
        assertThat(endEmail1).isNotEqualTo(endEmail2);
    }
}
