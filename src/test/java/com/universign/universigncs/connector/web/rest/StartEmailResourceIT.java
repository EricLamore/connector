package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.StartEmail;
import com.universign.universigncs.connector.repository.StartEmailRepository;
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
 * Integration tests for the {@Link StartEmailResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class StartEmailResourceIT {

    private static final String DEFAULT_START_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_START_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_START_BODY = "AAAAAAAAAA";
    private static final String UPDATED_START_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_END_BODY = "AAAAAAAAAA";
    private static final String UPDATED_END_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNATURE_LINK_BODY = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE_LINK_BODY = "BBBBBBBBBB";

    @Autowired
    private StartEmailRepository startEmailRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restStartEmailMockMvc;

    private StartEmail startEmail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StartEmailResource startEmailResource = new StartEmailResource(startEmailRepository);
        this.restStartEmailMockMvc = MockMvcBuilders.standaloneSetup(startEmailResource)
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
    public static StartEmail createEntity() {
        StartEmail startEmail = new StartEmail()
            .startSubject(DEFAULT_START_SUBJECT)
            .startBody(DEFAULT_START_BODY)
            .endBody(DEFAULT_END_BODY)
            .signatureLinkBody(DEFAULT_SIGNATURE_LINK_BODY);
        return startEmail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StartEmail createUpdatedEntity() {
        StartEmail startEmail = new StartEmail()
            .startSubject(UPDATED_START_SUBJECT)
            .startBody(UPDATED_START_BODY)
            .endBody(UPDATED_END_BODY)
            .signatureLinkBody(UPDATED_SIGNATURE_LINK_BODY);
        return startEmail;
    }

    @BeforeEach
    public void initTest() {
        startEmailRepository.deleteAll();
        startEmail = createEntity();
    }

    @Test
    public void createStartEmail() throws Exception {
        int databaseSizeBeforeCreate = startEmailRepository.findAll().size();

        // Create the StartEmail
        restStartEmailMockMvc.perform(post("/api/start-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(startEmail)))
            .andExpect(status().isCreated());

        // Validate the StartEmail in the database
        List<StartEmail> startEmailList = startEmailRepository.findAll();
        assertThat(startEmailList).hasSize(databaseSizeBeforeCreate + 1);
        StartEmail testStartEmail = startEmailList.get(startEmailList.size() - 1);
        assertThat(testStartEmail.getStartSubject()).isEqualTo(DEFAULT_START_SUBJECT);
        assertThat(testStartEmail.getStartBody()).isEqualTo(DEFAULT_START_BODY);
        assertThat(testStartEmail.getEndBody()).isEqualTo(DEFAULT_END_BODY);
        assertThat(testStartEmail.getSignatureLinkBody()).isEqualTo(DEFAULT_SIGNATURE_LINK_BODY);
    }

    @Test
    public void createStartEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = startEmailRepository.findAll().size();

        // Create the StartEmail with an existing ID
        startEmail.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStartEmailMockMvc.perform(post("/api/start-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(startEmail)))
            .andExpect(status().isBadRequest());

        // Validate the StartEmail in the database
        List<StartEmail> startEmailList = startEmailRepository.findAll();
        assertThat(startEmailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllStartEmails() throws Exception {
        // Initialize the database
        startEmailRepository.save(startEmail);

        // Get all the startEmailList
        restStartEmailMockMvc.perform(get("/api/start-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(startEmail.getId())))
            .andExpect(jsonPath("$.[*].startSubject").value(hasItem(DEFAULT_START_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].startBody").value(hasItem(DEFAULT_START_BODY.toString())))
            .andExpect(jsonPath("$.[*].endBody").value(hasItem(DEFAULT_END_BODY.toString())))
            .andExpect(jsonPath("$.[*].signatureLinkBody").value(hasItem(DEFAULT_SIGNATURE_LINK_BODY.toString())));
    }
    
    @Test
    public void getStartEmail() throws Exception {
        // Initialize the database
        startEmailRepository.save(startEmail);

        // Get the startEmail
        restStartEmailMockMvc.perform(get("/api/start-emails/{id}", startEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(startEmail.getId()))
            .andExpect(jsonPath("$.startSubject").value(DEFAULT_START_SUBJECT.toString()))
            .andExpect(jsonPath("$.startBody").value(DEFAULT_START_BODY.toString()))
            .andExpect(jsonPath("$.endBody").value(DEFAULT_END_BODY.toString()))
            .andExpect(jsonPath("$.signatureLinkBody").value(DEFAULT_SIGNATURE_LINK_BODY.toString()));
    }

    @Test
    public void getNonExistingStartEmail() throws Exception {
        // Get the startEmail
        restStartEmailMockMvc.perform(get("/api/start-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStartEmail() throws Exception {
        // Initialize the database
        startEmailRepository.save(startEmail);

        int databaseSizeBeforeUpdate = startEmailRepository.findAll().size();

        // Update the startEmail
        StartEmail updatedStartEmail = startEmailRepository.findById(startEmail.getId()).get();
        updatedStartEmail
            .startSubject(UPDATED_START_SUBJECT)
            .startBody(UPDATED_START_BODY)
            .endBody(UPDATED_END_BODY)
            .signatureLinkBody(UPDATED_SIGNATURE_LINK_BODY);

        restStartEmailMockMvc.perform(put("/api/start-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStartEmail)))
            .andExpect(status().isOk());

        // Validate the StartEmail in the database
        List<StartEmail> startEmailList = startEmailRepository.findAll();
        assertThat(startEmailList).hasSize(databaseSizeBeforeUpdate);
        StartEmail testStartEmail = startEmailList.get(startEmailList.size() - 1);
        assertThat(testStartEmail.getStartSubject()).isEqualTo(UPDATED_START_SUBJECT);
        assertThat(testStartEmail.getStartBody()).isEqualTo(UPDATED_START_BODY);
        assertThat(testStartEmail.getEndBody()).isEqualTo(UPDATED_END_BODY);
        assertThat(testStartEmail.getSignatureLinkBody()).isEqualTo(UPDATED_SIGNATURE_LINK_BODY);
    }

    @Test
    public void updateNonExistingStartEmail() throws Exception {
        int databaseSizeBeforeUpdate = startEmailRepository.findAll().size();

        // Create the StartEmail

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStartEmailMockMvc.perform(put("/api/start-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(startEmail)))
            .andExpect(status().isBadRequest());

        // Validate the StartEmail in the database
        List<StartEmail> startEmailList = startEmailRepository.findAll();
        assertThat(startEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteStartEmail() throws Exception {
        // Initialize the database
        startEmailRepository.save(startEmail);

        int databaseSizeBeforeDelete = startEmailRepository.findAll().size();

        // Delete the startEmail
        restStartEmailMockMvc.perform(delete("/api/start-emails/{id}", startEmail.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<StartEmail> startEmailList = startEmailRepository.findAll();
        assertThat(startEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StartEmail.class);
        StartEmail startEmail1 = new StartEmail();
        startEmail1.setId("id1");
        StartEmail startEmail2 = new StartEmail();
        startEmail2.setId(startEmail1.getId());
        assertThat(startEmail1).isEqualTo(startEmail2);
        startEmail2.setId("id2");
        assertThat(startEmail1).isNotEqualTo(startEmail2);
        startEmail1.setId(null);
        assertThat(startEmail1).isNotEqualTo(startEmail2);
    }
}
