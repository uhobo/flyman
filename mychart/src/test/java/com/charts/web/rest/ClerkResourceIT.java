package com.charts.web.rest;

import com.charts.MychartApp;
import com.charts.domain.Clerk;
import com.charts.repository.ClerkRepository;
import com.charts.web.rest.errors.ExceptionTranslator;

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

import static com.charts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClerkResource} REST controller.
 */
@SpringBootTest(classes = MychartApp.class)
public class ClerkResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private ClerkRepository clerkRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restClerkMockMvc;

    private Clerk clerk;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClerkResource clerkResource = new ClerkResource(clerkRepository);
        this.restClerkMockMvc = MockMvcBuilders.standaloneSetup(clerkResource)
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
    public static Clerk createEntity() {
        Clerk clerk = new Clerk()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return clerk;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clerk createUpdatedEntity() {
        Clerk clerk = new Clerk()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return clerk;
    }

    @BeforeEach
    public void initTest() {
        clerkRepository.deleteAll();
        clerk = createEntity();
    }

    @Test
    public void createClerk() throws Exception {
        int databaseSizeBeforeCreate = clerkRepository.findAll().size();

        // Create the Clerk
        restClerkMockMvc.perform(post("/api/clerks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clerk)))
            .andExpect(status().isCreated());

        // Validate the Clerk in the database
        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeCreate + 1);
        Clerk testClerk = clerkList.get(clerkList.size() - 1);
        assertThat(testClerk.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testClerk.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    public void createClerkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clerkRepository.findAll().size();

        // Create the Clerk with an existing ID
        clerk.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restClerkMockMvc.perform(post("/api/clerks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clerk)))
            .andExpect(status().isBadRequest());

        // Validate the Clerk in the database
        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clerkRepository.findAll().size();
        // set the field null
        clerk.setFirstName(null);

        // Create the Clerk, which fails.

        restClerkMockMvc.perform(post("/api/clerks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clerk)))
            .andExpect(status().isBadRequest());

        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllClerks() throws Exception {
        // Initialize the database
        clerkRepository.save(clerk);

        // Get all the clerkList
        restClerkMockMvc.perform(get("/api/clerks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clerk.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @Test
    public void getClerk() throws Exception {
        // Initialize the database
        clerkRepository.save(clerk);

        // Get the clerk
        restClerkMockMvc.perform(get("/api/clerks/{id}", clerk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clerk.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    public void getNonExistingClerk() throws Exception {
        // Get the clerk
        restClerkMockMvc.perform(get("/api/clerks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClerk() throws Exception {
        // Initialize the database
        clerkRepository.save(clerk);

        int databaseSizeBeforeUpdate = clerkRepository.findAll().size();

        // Update the clerk
        Clerk updatedClerk = clerkRepository.findById(clerk.getId()).get();
        updatedClerk
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restClerkMockMvc.perform(put("/api/clerks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClerk)))
            .andExpect(status().isOk());

        // Validate the Clerk in the database
        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeUpdate);
        Clerk testClerk = clerkList.get(clerkList.size() - 1);
        assertThat(testClerk.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testClerk.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    public void updateNonExistingClerk() throws Exception {
        int databaseSizeBeforeUpdate = clerkRepository.findAll().size();

        // Create the Clerk

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClerkMockMvc.perform(put("/api/clerks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clerk)))
            .andExpect(status().isBadRequest());

        // Validate the Clerk in the database
        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteClerk() throws Exception {
        // Initialize the database
        clerkRepository.save(clerk);

        int databaseSizeBeforeDelete = clerkRepository.findAll().size();

        // Delete the clerk
        restClerkMockMvc.perform(delete("/api/clerks/{id}", clerk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clerk> clerkList = clerkRepository.findAll();
        assertThat(clerkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
