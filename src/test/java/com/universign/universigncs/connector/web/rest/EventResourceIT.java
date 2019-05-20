package com.universign.universigncs.connector.web.rest;

import com.universign.universigncs.connector.ConnectorApp;
import com.universign.universigncs.connector.domain.Event;
import com.universign.universigncs.connector.repository.EventRepository;
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


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.universign.universigncs.connector.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universign.universigncs.connector.domain.enumeration.EventType;
import com.universign.universigncs.connector.domain.enumeration.ConnectorType;
import com.universign.universigncs.connector.domain.enumeration.ConnectorType;
/**
 * Integration tests for the {@Link EventResource} REST controller.
 */
@SpringBootTest(classes = ConnectorApp.class)
public class EventResourceIT {

    private static final String DEFAULT_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MASTER_EVENT = false;
    private static final Boolean UPDATED_MASTER_EVENT = true;

    private static final EventType DEFAULT_TYPE = EventType.SYNCHRONIZATION;
    private static final EventType UPDATED_TYPE = EventType.ADD_ACCOUNT;

    private static final ConnectorType DEFAULT_FROM = ConnectorType.PPM;
    private static final ConnectorType UPDATED_FROM = ConnectorType.CRM;

    private static final ConnectorType DEFAULT_TO = ConnectorType.PPM;
    private static final ConnectorType UPDATED_TO = ConnectorType.CRM;

    private static final String DEFAULT_EXCEPTION = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CHILD_SIZE = 1;
    private static final Integer UPDATED_CHILD_SIZE = 2;

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEventMockMvc;

    private Event event;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventRepository);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity() {
        Event event = new Event()
            .parentId(DEFAULT_PARENT_ID)
            .masterEvent(DEFAULT_MASTER_EVENT)
            .type(DEFAULT_TYPE)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .exception(DEFAULT_EXCEPTION)
            .message(DEFAULT_MESSAGE)
            .creationDate(DEFAULT_CREATION_DATE)
            .childSize(DEFAULT_CHILD_SIZE)
            .parameter(DEFAULT_PARAMETER);
        return event;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity() {
        Event event = new Event()
            .parentId(UPDATED_PARENT_ID)
            .masterEvent(UPDATED_MASTER_EVENT)
            .type(UPDATED_TYPE)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .exception(UPDATED_EXCEPTION)
            .message(UPDATED_MESSAGE)
            .creationDate(UPDATED_CREATION_DATE)
            .childSize(UPDATED_CHILD_SIZE)
            .parameter(UPDATED_PARAMETER);
        return event;
    }

    @BeforeEach
    public void initTest() {
        eventRepository.deleteAll();
        event = createEntity();
    }

    @Test
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testEvent.isMasterEvent()).isEqualTo(DEFAULT_MASTER_EVENT);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testEvent.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testEvent.getException()).isEqualTo(DEFAULT_EXCEPTION);
        assertThat(testEvent.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEvent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testEvent.getChildSize()).isEqualTo(DEFAULT_CHILD_SIZE);
        assertThat(testEvent.getParameter()).isEqualTo(DEFAULT_PARAMETER);
    }

    @Test
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.toString())))
            .andExpect(jsonPath("$.[*].masterEvent").value(hasItem(DEFAULT_MASTER_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].exception").value(hasItem(DEFAULT_EXCEPTION.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].childSize").value(hasItem(DEFAULT_CHILD_SIZE)))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())));
    }
    
    @Test
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.toString()))
            .andExpect(jsonPath("$.masterEvent").value(DEFAULT_MASTER_EVENT.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.exception").value(DEFAULT_EXCEPTION.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.childSize").value(DEFAULT_CHILD_SIZE))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER.toString()));
    }

    @Test
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        updatedEvent
            .parentId(UPDATED_PARENT_ID)
            .masterEvent(UPDATED_MASTER_EVENT)
            .type(UPDATED_TYPE)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .exception(UPDATED_EXCEPTION)
            .message(UPDATED_MESSAGE)
            .creationDate(UPDATED_CREATION_DATE)
            .childSize(UPDATED_CHILD_SIZE)
            .parameter(UPDATED_PARAMETER);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testEvent.isMasterEvent()).isEqualTo(UPDATED_MASTER_EVENT);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testEvent.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testEvent.getException()).isEqualTo(UPDATED_EXCEPTION);
        assertThat(testEvent.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEvent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEvent.getChildSize()).isEqualTo(UPDATED_CHILD_SIZE);
        assertThat(testEvent.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId("id1");
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId("id2");
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
