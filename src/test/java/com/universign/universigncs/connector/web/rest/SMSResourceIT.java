package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.SMS;
import com.universign.universigncs.connector.repository.SMSRepository;
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

import com.universign.universigncs.connector.domain.enumeration.Language;
/**
 * Integration tests for the {@Link SMSResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class SMSResourceIT {

    private static final String DEFAULT_SMS_AUTH_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SMS_AUTH_TEXT = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private SMSRepository sMSRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSMSMockMvc;

    private SMS sMS;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SMSResource sMSResource = new SMSResource(sMSRepository);
        this.restSMSMockMvc = MockMvcBuilders.standaloneSetup(sMSResource)
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
    public static SMS createEntity() {
        SMS sMS = new SMS()
            .smsAuthText(DEFAULT_SMS_AUTH_TEXT)
            .language(DEFAULT_LANGUAGE);
        return sMS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SMS createUpdatedEntity() {
        SMS sMS = new SMS()
            .smsAuthText(UPDATED_SMS_AUTH_TEXT)
            .language(UPDATED_LANGUAGE);
        return sMS;
    }

    @BeforeEach
    public void initTest() {
        sMSRepository.deleteAll();
        sMS = createEntity();
    }

    @Test
    public void createSMS() throws Exception {
        int databaseSizeBeforeCreate = sMSRepository.findAll().size();

        // Create the SMS
        restSMSMockMvc.perform(post("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sMS)))
            .andExpect(status().isCreated());

        // Validate the SMS in the database
        List<SMS> sMSList = sMSRepository.findAll();
        assertThat(sMSList).hasSize(databaseSizeBeforeCreate + 1);
        SMS testSMS = sMSList.get(sMSList.size() - 1);
        assertThat(testSMS.getSmsAuthText()).isEqualTo(DEFAULT_SMS_AUTH_TEXT);
        assertThat(testSMS.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    public void createSMSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sMSRepository.findAll().size();

        // Create the SMS with an existing ID
        sMS.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSMSMockMvc.perform(post("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sMS)))
            .andExpect(status().isBadRequest());

        // Validate the SMS in the database
        List<SMS> sMSList = sMSRepository.findAll();
        assertThat(sMSList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllSMS() throws Exception {
        // Initialize the database
        sMSRepository.save(sMS);

        // Get all the sMSList
        restSMSMockMvc.perform(get("/api/sms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sMS.getId())))
            .andExpect(jsonPath("$.[*].smsAuthText").value(hasItem(DEFAULT_SMS_AUTH_TEXT.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    public void getSMS() throws Exception {
        // Initialize the database
        sMSRepository.save(sMS);

        // Get the sMS
        restSMSMockMvc.perform(get("/api/sms/{id}", sMS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sMS.getId()))
            .andExpect(jsonPath("$.smsAuthText").value(DEFAULT_SMS_AUTH_TEXT.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    public void getNonExistingSMS() throws Exception {
        // Get the sMS
        restSMSMockMvc.perform(get("/api/sms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSMS() throws Exception {
        // Initialize the database
        sMSRepository.save(sMS);

        int databaseSizeBeforeUpdate = sMSRepository.findAll().size();

        // Update the sMS
        SMS updatedSMS = sMSRepository.findById(sMS.getId()).get();
        updatedSMS
            .smsAuthText(UPDATED_SMS_AUTH_TEXT)
            .language(UPDATED_LANGUAGE);

        restSMSMockMvc.perform(put("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSMS)))
            .andExpect(status().isOk());

        // Validate the SMS in the database
        List<SMS> sMSList = sMSRepository.findAll();
        assertThat(sMSList).hasSize(databaseSizeBeforeUpdate);
        SMS testSMS = sMSList.get(sMSList.size() - 1);
        assertThat(testSMS.getSmsAuthText()).isEqualTo(UPDATED_SMS_AUTH_TEXT);
        assertThat(testSMS.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    public void updateNonExistingSMS() throws Exception {
        int databaseSizeBeforeUpdate = sMSRepository.findAll().size();

        // Create the SMS

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSMSMockMvc.perform(put("/api/sms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sMS)))
            .andExpect(status().isBadRequest());

        // Validate the SMS in the database
        List<SMS> sMSList = sMSRepository.findAll();
        assertThat(sMSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSMS() throws Exception {
        // Initialize the database
        sMSRepository.save(sMS);

        int databaseSizeBeforeDelete = sMSRepository.findAll().size();

        // Delete the sMS
        restSMSMockMvc.perform(delete("/api/sms/{id}", sMS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SMS> sMSList = sMSRepository.findAll();
        assertThat(sMSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SMS.class);
        SMS sMS1 = new SMS();
        sMS1.setId("id1");
        SMS sMS2 = new SMS();
        sMS2.setId(sMS1.getId());
        assertThat(sMS1).isEqualTo(sMS2);
        sMS2.setId("id2");
        assertThat(sMS1).isNotEqualTo(sMS2);
        sMS1.setId(null);
        assertThat(sMS1).isNotEqualTo(sMS2);
    }
}
