package com.geller.charts.web.rest;

import com.geller.charts.ChartsApp;

import com.geller.charts.domain.Asked;
import com.geller.charts.repository.AskedRepository;
import com.geller.charts.service.AskedService;
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
 * Test class for the AskedResource REST controller.
 *
 * @see AskedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChartsApp.class)
public class AskedResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AskedRepository askedRepository;

    @Mock
    private AskedRepository askedRepositoryMock;
    

    @Mock
    private AskedService askedServiceMock;

    @Autowired
    private AskedService askedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAskedMockMvc;

    private Asked asked;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AskedResource askedResource = new AskedResource(askedService);
        this.restAskedMockMvc = MockMvcBuilders.standaloneSetup(askedResource)
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
    public static Asked createEntity() {
        Asked asked = (Asked) new Asked()
            .description(DEFAULT_DESCRIPTION);
        return asked;
    }

    @Before
    public void initTest() {
        askedRepository.deleteAll();
        asked = createEntity();
    }

    @Test
    public void createAsked() throws Exception {
        int databaseSizeBeforeCreate = askedRepository.findAll().size();

        // Create the Asked
        restAskedMockMvc.perform(post("/api/askeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asked)))
            .andExpect(status().isCreated());

        // Validate the Asked in the database
        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeCreate + 1);
        Asked testAsked = askedList.get(askedList.size() - 1);
        assertThat(testAsked.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createAskedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = askedRepository.findAll().size();

        // Create the Asked with an existing ID
        asked.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAskedMockMvc.perform(post("/api/askeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asked)))
            .andExpect(status().isBadRequest());

        // Validate the Asked in the database
        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = askedRepository.findAll().size();
        // set the field null
        asked.setDescription(null);

        // Create the Asked, which fails.

        restAskedMockMvc.perform(post("/api/askeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asked)))
            .andExpect(status().isBadRequest());

        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAskeds() throws Exception {
        // Initialize the database
        askedRepository.save(asked);

        // Get all the askedList
        restAskedMockMvc.perform(get("/api/askeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asked.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    public void getAllAskedsWithEagerRelationshipsIsEnabled() throws Exception {
        AskedResource askedResource = new AskedResource(askedServiceMock);
        when(askedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAskedMockMvc = MockMvcBuilders.standaloneSetup(askedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAskedMockMvc.perform(get("/api/askeds?eagerload=true"))
        .andExpect(status().isOk());

        verify(askedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllAskedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AskedResource askedResource = new AskedResource(askedServiceMock);
            when(askedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAskedMockMvc = MockMvcBuilders.standaloneSetup(askedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAskedMockMvc.perform(get("/api/askeds?eagerload=true"))
        .andExpect(status().isOk());

            verify(askedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getAsked() throws Exception {
        // Initialize the database
        askedRepository.save(asked);

        // Get the asked
        restAskedMockMvc.perform(get("/api/askeds/{id}", asked.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asked.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingAsked() throws Exception {
        // Get the asked
        restAskedMockMvc.perform(get("/api/askeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAsked() throws Exception {
        // Initialize the database
        askedService.save(asked);

        int databaseSizeBeforeUpdate = askedRepository.findAll().size();

        // Update the asked
        Asked updatedAsked = askedRepository.findById(asked.getId()).get();
        updatedAsked
            .description(UPDATED_DESCRIPTION);

        restAskedMockMvc.perform(put("/api/askeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsked)))
            .andExpect(status().isOk());

        // Validate the Asked in the database
        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeUpdate);
        Asked testAsked = askedList.get(askedList.size() - 1);
        assertThat(testAsked.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingAsked() throws Exception {
        int databaseSizeBeforeUpdate = askedRepository.findAll().size();

        // Create the Asked

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAskedMockMvc.perform(put("/api/askeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asked)))
            .andExpect(status().isBadRequest());

        // Validate the Asked in the database
        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAsked() throws Exception {
        // Initialize the database
        askedService.save(asked);

        int databaseSizeBeforeDelete = askedRepository.findAll().size();

        // Get the asked
        restAskedMockMvc.perform(delete("/api/askeds/{id}", asked.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asked> askedList = askedRepository.findAll();
        assertThat(askedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asked.class);
        Asked asked1 = new Asked();
        asked1.setId("id1");
        Asked asked2 = new Asked();
        asked2.setId(asked1.getId());
        assertThat(asked1).isEqualTo(asked2);
        asked2.setId("id2");
        assertThat(asked1).isNotEqualTo(asked2);
        asked1.setId(null);
        assertThat(asked1).isNotEqualTo(asked2);
    }
}
