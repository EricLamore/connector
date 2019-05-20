package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.Profile;
import com.universign.universigncs.connector.repository.ProfileRepository;
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

import com.universign.universigncs.connector.domain.enumeration.Environment;
/**
 * Integration tests for the {@Link ProfileResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class ProfileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Environment DEFAULT_ENVIRONMENT = Environment.SANDBOX;
    private static final Environment UPDATED_ENVIRONMENT = Environment.PRODUCTION;

    private static final Integer DEFAULT_LIFE_TRANSACTION = 1;
    private static final Integer UPDATED_LIFE_TRANSACTION = 2;

    private static final String DEFAULT_CALLBACK_URL = "AAAAAAAAAA";
    private static final String UPDATED_CALLBACK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REDIRECTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_REDIRECTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_ANALYTICS_ID = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_ANALYTICS_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DISPLAYED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAYED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLICK_URL = "AAAAAAAAAA";
    private static final String UPDATED_CLICK_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SMS_OTP_LIFETIME = 1;
    private static final Integer UPDATED_SMS_OTP_LIFETIME = 2;

    private static final String DEFAULT_LOGO_ALIGN = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_ALIGN = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_INTRO = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_INTRO = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_TEXTINTRO = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_TEXTINTRO = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_BODY = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_TEXT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_TEXT_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_BUTON = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_BUTON = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_TEXT_BUTTON = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_TEXT_BUTTON = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_TEXT_BUTTON = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_TEXT_BUTTON = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_ALIGN = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_ALIGN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STORAGE = false;
    private static final Boolean UPDATED_STORAGE = true;

    private static final String DEFAULT_STORAGE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE_SERVICE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MULTI_STAMP = false;
    private static final Boolean UPDATED_MULTI_STAMP = true;

    private static final Boolean DEFAULT_MERGE = false;
    private static final Boolean UPDATED_MERGE = true;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileRepository);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity() {
        Profile profile = new Profile()
            .name(DEFAULT_NAME)
            .environment(DEFAULT_ENVIRONMENT)
            .lifeTransaction(DEFAULT_LIFE_TRANSACTION)
            .callbackUrl(DEFAULT_CALLBACK_URL)
            .redirectionUrl(DEFAULT_REDIRECTION_URL)
            .googleAnalyticsId(DEFAULT_GOOGLE_ANALYTICS_ID)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .displayedName(DEFAULT_DISPLAYED_NAME)
            .clickUrl(DEFAULT_CLICK_URL)
            .smsOtpLifetime(DEFAULT_SMS_OTP_LIFETIME)
            .logoAlign(DEFAULT_LOGO_ALIGN)
            .colorIntro(DEFAULT_COLOR_INTRO)
            .sizeTextintro(DEFAULT_SIZE_TEXTINTRO)
            .colorBody(DEFAULT_COLOR_BODY)
            .sizeTextBody(DEFAULT_SIZE_TEXT_BODY)
            .colorButon(DEFAULT_COLOR_BUTON)
            .sizeTextButton(DEFAULT_SIZE_TEXT_BUTTON)
            .colorTextButton(DEFAULT_COLOR_TEXT_BUTTON)
            .textAlign(DEFAULT_TEXT_ALIGN)
            .storage(DEFAULT_STORAGE)
            .storageService(DEFAULT_STORAGE_SERVICE)
            .multiStamp(DEFAULT_MULTI_STAMP)
            .merge(DEFAULT_MERGE);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity() {
        Profile profile = new Profile()
            .name(UPDATED_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .lifeTransaction(UPDATED_LIFE_TRANSACTION)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .redirectionUrl(UPDATED_REDIRECTION_URL)
            .googleAnalyticsId(UPDATED_GOOGLE_ANALYTICS_ID)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .displayedName(UPDATED_DISPLAYED_NAME)
            .clickUrl(UPDATED_CLICK_URL)
            .smsOtpLifetime(UPDATED_SMS_OTP_LIFETIME)
            .logoAlign(UPDATED_LOGO_ALIGN)
            .colorIntro(UPDATED_COLOR_INTRO)
            .sizeTextintro(UPDATED_SIZE_TEXTINTRO)
            .colorBody(UPDATED_COLOR_BODY)
            .sizeTextBody(UPDATED_SIZE_TEXT_BODY)
            .colorButon(UPDATED_COLOR_BUTON)
            .sizeTextButton(UPDATED_SIZE_TEXT_BUTTON)
            .colorTextButton(UPDATED_COLOR_TEXT_BUTTON)
            .textAlign(UPDATED_TEXT_ALIGN)
            .storage(UPDATED_STORAGE)
            .storageService(UPDATED_STORAGE_SERVICE)
            .multiStamp(UPDATED_MULTI_STAMP)
            .merge(UPDATED_MERGE);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profileRepository.deleteAll();
        profile = createEntity();
    }

    @Test
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfile.getEnvironment()).isEqualTo(DEFAULT_ENVIRONMENT);
        assertThat(testProfile.getLifeTransaction()).isEqualTo(DEFAULT_LIFE_TRANSACTION);
        assertThat(testProfile.getCallbackUrl()).isEqualTo(DEFAULT_CALLBACK_URL);
        assertThat(testProfile.getRedirectionUrl()).isEqualTo(DEFAULT_REDIRECTION_URL);
        assertThat(testProfile.getGoogleAnalyticsId()).isEqualTo(DEFAULT_GOOGLE_ANALYTICS_ID);
        assertThat(testProfile.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testProfile.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testProfile.getDisplayedName()).isEqualTo(DEFAULT_DISPLAYED_NAME);
        assertThat(testProfile.getClickUrl()).isEqualTo(DEFAULT_CLICK_URL);
        assertThat(testProfile.getSmsOtpLifetime()).isEqualTo(DEFAULT_SMS_OTP_LIFETIME);
        assertThat(testProfile.getLogoAlign()).isEqualTo(DEFAULT_LOGO_ALIGN);
        assertThat(testProfile.getColorIntro()).isEqualTo(DEFAULT_COLOR_INTRO);
        assertThat(testProfile.getSizeTextintro()).isEqualTo(DEFAULT_SIZE_TEXTINTRO);
        assertThat(testProfile.getColorBody()).isEqualTo(DEFAULT_COLOR_BODY);
        assertThat(testProfile.getSizeTextBody()).isEqualTo(DEFAULT_SIZE_TEXT_BODY);
        assertThat(testProfile.getColorButon()).isEqualTo(DEFAULT_COLOR_BUTON);
        assertThat(testProfile.getSizeTextButton()).isEqualTo(DEFAULT_SIZE_TEXT_BUTTON);
        assertThat(testProfile.getColorTextButton()).isEqualTo(DEFAULT_COLOR_TEXT_BUTTON);
        assertThat(testProfile.getTextAlign()).isEqualTo(DEFAULT_TEXT_ALIGN);
        assertThat(testProfile.isStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testProfile.getStorageService()).isEqualTo(DEFAULT_STORAGE_SERVICE);
        assertThat(testProfile.isMultiStamp()).isEqualTo(DEFAULT_MULTI_STAMP);
        assertThat(testProfile.isMerge()).isEqualTo(DEFAULT_MERGE);
    }

    @Test
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT.toString())))
            .andExpect(jsonPath("$.[*].lifeTransaction").value(hasItem(DEFAULT_LIFE_TRANSACTION)))
            .andExpect(jsonPath("$.[*].callbackUrl").value(hasItem(DEFAULT_CALLBACK_URL.toString())))
            .andExpect(jsonPath("$.[*].redirectionUrl").value(hasItem(DEFAULT_REDIRECTION_URL.toString())))
            .andExpect(jsonPath("$.[*].googleAnalyticsId").value(hasItem(DEFAULT_GOOGLE_ANALYTICS_ID.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].displayedName").value(hasItem(DEFAULT_DISPLAYED_NAME.toString())))
            .andExpect(jsonPath("$.[*].clickUrl").value(hasItem(DEFAULT_CLICK_URL.toString())))
            .andExpect(jsonPath("$.[*].smsOtpLifetime").value(hasItem(DEFAULT_SMS_OTP_LIFETIME)))
            .andExpect(jsonPath("$.[*].logoAlign").value(hasItem(DEFAULT_LOGO_ALIGN.toString())))
            .andExpect(jsonPath("$.[*].colorIntro").value(hasItem(DEFAULT_COLOR_INTRO.toString())))
            .andExpect(jsonPath("$.[*].sizeTextintro").value(hasItem(DEFAULT_SIZE_TEXTINTRO.toString())))
            .andExpect(jsonPath("$.[*].colorBody").value(hasItem(DEFAULT_COLOR_BODY.toString())))
            .andExpect(jsonPath("$.[*].sizeTextBody").value(hasItem(DEFAULT_SIZE_TEXT_BODY.toString())))
            .andExpect(jsonPath("$.[*].colorButon").value(hasItem(DEFAULT_COLOR_BUTON.toString())))
            .andExpect(jsonPath("$.[*].sizeTextButton").value(hasItem(DEFAULT_SIZE_TEXT_BUTTON.toString())))
            .andExpect(jsonPath("$.[*].colorTextButton").value(hasItem(DEFAULT_COLOR_TEXT_BUTTON.toString())))
            .andExpect(jsonPath("$.[*].textAlign").value(hasItem(DEFAULT_TEXT_ALIGN.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].storageService").value(hasItem(DEFAULT_STORAGE_SERVICE.toString())))
            .andExpect(jsonPath("$.[*].multiStamp").value(hasItem(DEFAULT_MULTI_STAMP.booleanValue())))
            .andExpect(jsonPath("$.[*].merge").value(hasItem(DEFAULT_MERGE.booleanValue())));
    }
    
    @Test
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT.toString()))
            .andExpect(jsonPath("$.lifeTransaction").value(DEFAULT_LIFE_TRANSACTION))
            .andExpect(jsonPath("$.callbackUrl").value(DEFAULT_CALLBACK_URL.toString()))
            .andExpect(jsonPath("$.redirectionUrl").value(DEFAULT_REDIRECTION_URL.toString()))
            .andExpect(jsonPath("$.googleAnalyticsId").value(DEFAULT_GOOGLE_ANALYTICS_ID.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.displayedName").value(DEFAULT_DISPLAYED_NAME.toString()))
            .andExpect(jsonPath("$.clickUrl").value(DEFAULT_CLICK_URL.toString()))
            .andExpect(jsonPath("$.smsOtpLifetime").value(DEFAULT_SMS_OTP_LIFETIME))
            .andExpect(jsonPath("$.logoAlign").value(DEFAULT_LOGO_ALIGN.toString()))
            .andExpect(jsonPath("$.colorIntro").value(DEFAULT_COLOR_INTRO.toString()))
            .andExpect(jsonPath("$.sizeTextintro").value(DEFAULT_SIZE_TEXTINTRO.toString()))
            .andExpect(jsonPath("$.colorBody").value(DEFAULT_COLOR_BODY.toString()))
            .andExpect(jsonPath("$.sizeTextBody").value(DEFAULT_SIZE_TEXT_BODY.toString()))
            .andExpect(jsonPath("$.colorButon").value(DEFAULT_COLOR_BUTON.toString()))
            .andExpect(jsonPath("$.sizeTextButton").value(DEFAULT_SIZE_TEXT_BUTTON.toString()))
            .andExpect(jsonPath("$.colorTextButton").value(DEFAULT_COLOR_TEXT_BUTTON.toString()))
            .andExpect(jsonPath("$.textAlign").value(DEFAULT_TEXT_ALIGN.toString()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.booleanValue()))
            .andExpect(jsonPath("$.storageService").value(DEFAULT_STORAGE_SERVICE.toString()))
            .andExpect(jsonPath("$.multiStamp").value(DEFAULT_MULTI_STAMP.booleanValue()))
            .andExpect(jsonPath("$.merge").value(DEFAULT_MERGE.booleanValue()));
    }

    @Test
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        updatedProfile
            .name(UPDATED_NAME)
            .environment(UPDATED_ENVIRONMENT)
            .lifeTransaction(UPDATED_LIFE_TRANSACTION)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .redirectionUrl(UPDATED_REDIRECTION_URL)
            .googleAnalyticsId(UPDATED_GOOGLE_ANALYTICS_ID)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .displayedName(UPDATED_DISPLAYED_NAME)
            .clickUrl(UPDATED_CLICK_URL)
            .smsOtpLifetime(UPDATED_SMS_OTP_LIFETIME)
            .logoAlign(UPDATED_LOGO_ALIGN)
            .colorIntro(UPDATED_COLOR_INTRO)
            .sizeTextintro(UPDATED_SIZE_TEXTINTRO)
            .colorBody(UPDATED_COLOR_BODY)
            .sizeTextBody(UPDATED_SIZE_TEXT_BODY)
            .colorButon(UPDATED_COLOR_BUTON)
            .sizeTextButton(UPDATED_SIZE_TEXT_BUTTON)
            .colorTextButton(UPDATED_COLOR_TEXT_BUTTON)
            .textAlign(UPDATED_TEXT_ALIGN)
            .storage(UPDATED_STORAGE)
            .storageService(UPDATED_STORAGE_SERVICE)
            .multiStamp(UPDATED_MULTI_STAMP)
            .merge(UPDATED_MERGE);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getEnvironment()).isEqualTo(UPDATED_ENVIRONMENT);
        assertThat(testProfile.getLifeTransaction()).isEqualTo(UPDATED_LIFE_TRANSACTION);
        assertThat(testProfile.getCallbackUrl()).isEqualTo(UPDATED_CALLBACK_URL);
        assertThat(testProfile.getRedirectionUrl()).isEqualTo(UPDATED_REDIRECTION_URL);
        assertThat(testProfile.getGoogleAnalyticsId()).isEqualTo(UPDATED_GOOGLE_ANALYTICS_ID);
        assertThat(testProfile.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testProfile.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testProfile.getDisplayedName()).isEqualTo(UPDATED_DISPLAYED_NAME);
        assertThat(testProfile.getClickUrl()).isEqualTo(UPDATED_CLICK_URL);
        assertThat(testProfile.getSmsOtpLifetime()).isEqualTo(UPDATED_SMS_OTP_LIFETIME);
        assertThat(testProfile.getLogoAlign()).isEqualTo(UPDATED_LOGO_ALIGN);
        assertThat(testProfile.getColorIntro()).isEqualTo(UPDATED_COLOR_INTRO);
        assertThat(testProfile.getSizeTextintro()).isEqualTo(UPDATED_SIZE_TEXTINTRO);
        assertThat(testProfile.getColorBody()).isEqualTo(UPDATED_COLOR_BODY);
        assertThat(testProfile.getSizeTextBody()).isEqualTo(UPDATED_SIZE_TEXT_BODY);
        assertThat(testProfile.getColorButon()).isEqualTo(UPDATED_COLOR_BUTON);
        assertThat(testProfile.getSizeTextButton()).isEqualTo(UPDATED_SIZE_TEXT_BUTTON);
        assertThat(testProfile.getColorTextButton()).isEqualTo(UPDATED_COLOR_TEXT_BUTTON);
        assertThat(testProfile.getTextAlign()).isEqualTo(UPDATED_TEXT_ALIGN);
        assertThat(testProfile.isStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testProfile.getStorageService()).isEqualTo(UPDATED_STORAGE_SERVICE);
        assertThat(testProfile.isMultiStamp()).isEqualTo(UPDATED_MULTI_STAMP);
        assertThat(testProfile.isMerge()).isEqualTo(UPDATED_MERGE);
    }

    @Test
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId("id1");
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId("id2");
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }
}
