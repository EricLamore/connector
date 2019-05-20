package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.Emails;
import com.universign.universigncs.connector.repository.EmailsRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;


import java.util.List;

import static com.universign.universigncs.connector.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.connector.domain.enumeration.Language;
/**
 * Integration tests for the {@Link EmailsResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class EmailsResourceIT {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private EmailsRepository emailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEmailsMockMvc;

    private Emails emails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailsResource emailsResource = new EmailsResource(emailsRepository);
        this.restEmailsMockMvc = MockMvcBuilders.standaloneSetup(emailsResource)
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
    public static Emails createEntity() {
        Emails emails = new Emails()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .language(DEFAULT_LANGUAGE);
        return emails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emails createUpdatedEntity() {
        Emails emails = new Emails()
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .language(UPDATED_LANGUAGE);
        return emails;
    }

    @BeforeEach
    public void initTest() {
        emailsRepository.deleteAll();
        emails = createEntity();
    }

    @Test
    public void createEmails() throws Exception {
        int databaseSizeBeforeCreate = emailsRepository.findAll().size();

        // Create the Emails
        restEmailsMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isCreated());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate + 1);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testEmails.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testEmails.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    public void createEmailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailsRepository.findAll().size();

        // Create the Emails with an existing ID
        emails.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailsMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEmails() throws Exception {
        // Initialize the database
        emailsRepository.save(emails);

        // Get all the emailsList
        restEmailsMockMvc.perform(get("/api/emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emails.getId())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    public void getEmails() throws Exception {
        // Initialize the database
        emailsRepository.save(emails);

        // Get the emails
        restEmailsMockMvc.perform(get("/api/emails/{id}", emails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emails.getId()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    public void getNonExistingEmails() throws Exception {
        // Get the emails
        restEmailsMockMvc.perform(get("/api/emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmails() throws Exception {
        // Initialize the database
        emailsRepository.save(emails);

        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Update the emails
        Emails updatedEmails = emailsRepository.findById(emails.getId()).get();
        updatedEmails
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .language(UPDATED_LANGUAGE);

        restEmailsMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmails)))
            .andExpect(status().isOk());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
        Emails testEmails = emailsList.get(emailsList.size() - 1);
        assertThat(testEmails.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testEmails.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testEmails.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    public void updateNonExistingEmails() throws Exception {
        int databaseSizeBeforeUpdate = emailsRepository.findAll().size();

        // Create the Emails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailsMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emails)))
            .andExpect(status().isBadRequest());

        // Validate the Emails in the database
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEmails() throws Exception {
        // Initialize the database
        emailsRepository.save(emails);

        int databaseSizeBeforeDelete = emailsRepository.findAll().size();

        // Delete the emails
        restEmailsMockMvc.perform(delete("/api/emails/{id}", emails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Emails> emailsList = emailsRepository.findAll();
        assertThat(emailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emails.class);
        Emails emails1 = new Emails();
        emails1.setId("id1");
        Emails emails2 = new Emails();
        emails2.setId(emails1.getId());
        assertThat(emails1).isEqualTo(emails2);
        emails2.setId("id2");
        assertThat(emails1).isNotEqualTo(emails2);
        emails1.setId(null);
        assertThat(emails1).isNotEqualTo(emails2);
    }
}
