package com.geller.charts.web.rest;

import com.geller.charts.ChartsApp;

import com.geller.charts.domain.Responding;
import com.geller.charts.repository.RespondingRepository;
import com.geller.charts.service.RespondingService;
import com.geller.charts.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


import static com.geller.charts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RespondingResource REST controller.
 *
 * @see RespondingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChartsApp.class)
public class RespondingResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RespondingRepository respondingRepository;

    @Mock
    private RespondingRepository respondingRepositoryMock;
    

    @Mock
    private RespondingService respondingServiceMock;

    @Autowired
    private RespondingService respondingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRespondingMockMvc;

    private Responding responding;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RespondingResource respondingResource = new RespondingResource(respondingService);
        this.restRespondingMockMvc = MockMvcBuilders.standaloneSetup(respondingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responding createEntity() {
        Responding responding = new Responding()
            .description(DEFAULT_DESCRIPTION);
        return responding;
    }

    @Before
    public void initTest() {
        respondingRepository.deleteAll();
        responding = createEntity();
    }

    @Test
    public void createResponding() throws Exception {
        int databaseSizeBeforeCreate = respondingRepository.findAll().size();

        // Create the Responding
        restRespondingMockMvc.perform(post("/api/respondings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responding)))
            .andExpect(status().isCreated());

        // Validate the Responding in the database
        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeCreate + 1);
        Responding testResponding = respondingList.get(respondingList.size() - 1);
        assertThat(testResponding.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createRespondingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respondingRepository.findAll().size();

        // Create the Responding with an existing ID
        responding.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespondingMockMvc.perform(post("/api/respondings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responding)))
            .andExpect(status().isBadRequest());

        // Validate the Responding in the database
        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = respondingRepository.findAll().size();
        // set the field null
        responding.setDescription(null);

        // Create the Responding, which fails.

        restRespondingMockMvc.perform(post("/api/respondings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responding)))
            .andExpect(status().isBadRequest());

        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRespondings() throws Exception {
        // Initialize the database
        respondingRepository.save(responding);

        // Get all the respondingList
        restRespondingMockMvc.perform(get("/api/respondings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responding.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    public void getAllRespondingsWithEagerRelationshipsIsEnabled() throws Exception {
        RespondingResource respondingResource = new RespondingResource(respondingServiceMock);
        when(respondingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRespondingMockMvc = MockMvcBuilders.standaloneSetup(respondingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRespondingMockMvc.perform(get("/api/respondings?eagerload=true"))
        .andExpect(status().isOk());

        verify(respondingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRespondingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RespondingResource respondingResource = new RespondingResource(respondingServiceMock);
            when(respondingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRespondingMockMvc = MockMvcBuilders.standaloneSetup(respondingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRespondingMockMvc.perform(get("/api/respondings?eagerload=true"))
        .andExpect(status().isOk());

            verify(respondingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getResponding() throws Exception {
        // Initialize the database
        respondingRepository.save(responding);

        // Get the responding
        restRespondingMockMvc.perform(get("/api/respondings/{id}", responding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responding.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingResponding() throws Exception {
        // Get the responding
        restRespondingMockMvc.perform(get("/api/respondings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateResponding() throws Exception {
        // Initialize the database
        respondingService.save(responding);

        int databaseSizeBeforeUpdate = respondingRepository.findAll().size();

        // Update the responding
        Responding updatedResponding = respondingRepository.findById(responding.getId()).get();
        updatedResponding
            .description(UPDATED_DESCRIPTION);

        restRespondingMockMvc.perform(put("/api/respondings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponding)))
            .andExpect(status().isOk());

        // Validate the Responding in the database
        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeUpdate);
        Responding testResponding = respondingList.get(respondingList.size() - 1);
        assertThat(testResponding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingResponding() throws Exception {
        int databaseSizeBeforeUpdate = respondingRepository.findAll().size();

        // Create the Responding

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRespondingMockMvc.perform(put("/api/respondings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responding)))
            .andExpect(status().isBadRequest());

        // Validate the Responding in the database
        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteResponding() throws Exception {
        // Initialize the database
        respondingService.save(responding);

        int databaseSizeBeforeDelete = respondingRepository.findAll().size();

        // Get the responding
        restRespondingMockMvc.perform(delete("/api/respondings/{id}", responding.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Responding> respondingList = respondingRepository.findAll();
        assertThat(respondingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responding.class);
        Responding responding1 = new Responding();
        responding1.setId("id1");
        Responding responding2 = new Responding();
        responding2.setId(responding1.getId());
        assertThat(responding1).isEqualTo(responding2);
        responding2.setId("id2");
        assertThat(responding1).isNotEqualTo(responding2);
        responding1.setId(null);
        assertThat(responding1).isNotEqualTo(responding2);
    }
}
